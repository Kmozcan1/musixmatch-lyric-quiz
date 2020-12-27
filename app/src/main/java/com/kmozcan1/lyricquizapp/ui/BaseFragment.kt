package com.kmozcan1.lyricquizapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 25-Dec-20.
 */

abstract class BaseFragment<DataBindingClass: ViewDataBinding, ViewModelClass: ViewModel>
    : Fragment() {

    @Inject
    lateinit var mainActivity: MainActivity

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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Type safe data binding from abstract class
        binding = DataBindingUtil.inflate(
                inflater, layoutId(), container, false) as DataBindingClass
        onViewBound()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(getViewModelClass())
    }

    open fun onInternetConnected() { }

    open fun onInternetDisconnected() { }

    internal fun setSupportActionBar(isVisible: Boolean, title: String? = null) {
        mainActivity.supportActionBar?.title = title
        if (isVisible) {
            mainActivity.supportActionBar?.show()
        } else {
            mainActivity.supportActionBar?.hide()
        }

    }

    internal fun makeToast(toastMessage: String?) {
        mainActivity.makeToast(toastMessage)
    }

    internal fun getIsConnectedToInternet(): Boolean {
        return mainActivity.isConnectedToInternet
    }
}