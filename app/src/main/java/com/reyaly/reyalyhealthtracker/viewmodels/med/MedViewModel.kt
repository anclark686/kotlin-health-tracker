package com.reyaly.reyalyhealthtracker.viewmodels.med

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Med Tracker"
    }
    val text: LiveData<String> = _text

}