package com.reyaly.reyalyhealthtracker.viewmodels.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExerciseViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Exercise Tracker"
    }
    val text: LiveData<String> = _text
}