package com.kmozcan1.lyricquizapp.domain.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 25-Dec-20.
 */
class InternetManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val internetStateObject = PublishSubject.create<Boolean>()
    private var internetReceiver: InternetReceiver? = null

    fun getInternetState(): Observable<Boolean> {
        return internetStateObject
                .doOnSubscribe { registerReceiver() }
                .doOnDispose { unregisterReceiver() }
    }

    private fun registerReceiver() {
        internetReceiver = InternetReceiver()
        val connectionFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        context.registerReceiver(internetReceiver, connectionFilter)
    }

    private fun unregisterReceiver() {
        context.unregisterReceiver(internetReceiver)
    }

    /**
     * BroadcastReceiver for detecting internet connection changes
     */
    internal inner class InternetReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            var isConnected = false
            val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager.activeNetwork
                if (activeNetwork != null) {
                    connectivityManager.getNetworkCapabilities(activeNetwork)?.run {
                        isConnected = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                // getActiveNetworkInfo() is deprecated for API 29,
                // but only API 22 and below goes here
                connectivityManager.run {
                    if (activeNetworkInfo != null) {
                        isConnected = activeNetworkInfo!!.isConnected
                    }
                }
            }
            internetStateObject.onNext(isConnected)
        }
    }
}