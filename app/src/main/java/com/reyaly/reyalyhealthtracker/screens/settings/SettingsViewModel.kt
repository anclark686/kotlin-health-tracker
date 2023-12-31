package com.reyaly.reyalyhealthtracker.screens.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SettingsViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome "
    }
    val text: LiveData<String> = _text

    fun onSignOut() {
        auth.signOut()
    }
}