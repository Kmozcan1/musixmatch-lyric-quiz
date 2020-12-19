package com.kmozcan1.lyricquizapp.domain.model.viewstate

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData


/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
class CustomMutableLiveData<T : BaseObservable?> : MutableLiveData<T?>() {
    override fun setValue(value: T?) {
        super.setValue(value)

        //listen to property changes
        value!!.addOnPropertyChangedCallback(callback)
    }

    var callback: Observable.OnPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            value = value
        }
    }
}