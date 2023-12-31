package com.reyaly.reyalyhealthtracker.screens.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Food Tracker"
    }
    val text: LiveData<String> = _text
}