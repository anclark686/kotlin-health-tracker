package com.reyaly.reyalyhealthtracker.screens.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.reyaly.reyalyhealthtracker.screens.settings.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private fun deleteAccountFinal() {
        val user = auth.currentUser!!

        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Settings", "User account deleted.")
                }
            }
    }

//    private fun updatePwFinal() {
//        val user = auth.currentUser!!
//        val password =
//        user.updatePassword(password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("Settings", "User password updated.")
//                }
//            }
//    }

    fun onSignInResult(result: SignInResult, callback: () -> Unit, modify: String? = null) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }

        if (modify == "delete") {
            deleteAccountFinal()
        }

        callback()
    }

    fun resetState() {
        _state.update { SignInState() }
    }


}