package com.reyaly.reyalyhealthtracker.screens.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.common.ext.isValidPassword
import com.reyaly.reyalyhealthtracker.common.ext.passwordMatches
import com.reyaly.reyalyhealthtracker.screens.emailandpw.EmailAndPwUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome "
    }
    val text: LiveData<String> = _text

    fun onSignOut() {
        auth.signOut()
    }

    fun deleteAccount(
        onSignInRedirect: () -> Unit
    ) {
        val user = auth.currentUser!!
        onSignInRedirect()
//        user.delete()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("Settings", "User account deleted.")
//                } else {
//                    // TODO
//                }
//            }
    }
}