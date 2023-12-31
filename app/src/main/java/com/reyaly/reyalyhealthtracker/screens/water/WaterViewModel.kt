package com.reyaly.reyalyhealthtracker.screens.water

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WaterViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Water Tracker"
    }
    val text: LiveData<String> = _text
}