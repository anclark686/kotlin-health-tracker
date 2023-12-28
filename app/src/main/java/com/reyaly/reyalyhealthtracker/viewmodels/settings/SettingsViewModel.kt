package com.reyaly.reyalyhealthtracker.viewmodels.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "Welcome "
    }
    val text: LiveData<String> = _text

}