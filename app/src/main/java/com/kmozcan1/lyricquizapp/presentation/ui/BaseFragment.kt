package com.kmozcan1.lyricquizapp.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.kmozcan1.lyricquizapp.R
import javax.inject.Inject


/**
 * Created by Kadir Mert Özcan on 25-Dec-20.
 */

abstract class BaseFragment<DataBindingClass: ViewDataBinding, ViewModelClass: ViewModel>
    : Fragment() {

    @Inject
    lateinit var mainActivity: MainActivity

    @Inject
    lateinit var navController: NavController

    val appCompatActivity: AppCompatActivity by lazy {
        activity as AppCompatActivity
    }

    lateinit var binding: DataBindingClass
        private set

    lateinit var viewModel: ViewModelClass
        private set

    // Layout res id for to inflate with data binding
    abstract fun layoutId(): Int

    // Must be set for providing type safe view model
    abstract fun getViewModelClass(): Class<ViewModelClass>

    // Called just before onCreateView is finished
    abstract fun onViewBound()

    // Called just before onActivityCreated is finished
    abstract fun observe()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, layoutId(), container, false
        ) as DataBindingClass
        onViewBound()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(getViewModelClass())
        observe()
    }

    open fun onInternetConnected() { }

    open fun onInternetDisconnected() { }

    internal fun setSupportActionBar(isVisible: Boolean, title: String? = null) {
        mainActivity.supportActionBar?.run {
            this.title = title

            if (isVisible) {
                show()
            } else {
                hide()
            }
        }
    }

    internal fun makeToast(toastMessage: String?) {
        mainActivity.makeToast(toastMessage)
    }

    internal fun getIsConnectedToInternet(): Boolean {
        return mainActivity.isConnectedToInternet
    }
}