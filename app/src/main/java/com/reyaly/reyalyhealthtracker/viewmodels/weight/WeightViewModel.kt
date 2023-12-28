package com.reyaly.reyalyhealthtracker.viewmodels.weight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeightViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Weight Tracker"
    }
    val text: LiveData<String> = _text
}