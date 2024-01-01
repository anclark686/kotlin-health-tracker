package com.reyaly.reyalyhealthtracker.screens.changepw

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.common.ext.isValidPassword
import com.reyaly.reyalyhealthtracker.common.ext.passwordMatches
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChangePwViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(ChangePwUiState())
    val uiState = _uiState.asStateFlow()
    private val password
        get() = uiState.value.password

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(password = newValue)
        _uiState.value = _uiState.value.copy(passwordError = null)
    }

    fun onRepeatPasswordChange(newValue: String) {
        Log.d("signUp", "repeat pw = ${_uiState.value.repeatPassword}")
        Log.d("signUp", "new = $newValue")
        _uiState.value = _uiState.value.copy(repeatPassword = newValue)
        _uiState.value = _uiState.value.copy(repeatPasswordError = null)
    }

    fun changePassword(
        onSuccess: () -> Unit
    ) {
        val user = auth.currentUser!!

        if (!password.isValidPassword()) {
            Log.d("signUp", "Your password should have at least six digits...")
            _uiState.value = _uiState.value.copy(passwordError = "Your password should have at least six digits and include one digit, one lower case letter and one upper case letter.")
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            Log.d("signUp", "Passwords do not match.")
            _uiState.value = _uiState.value.copy(repeatPasswordError = "Passwords do not match.")
            return
        }

        user.updatePassword(password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Settings", "User password updated.")
                    onSuccess()
                } else {
                    Log.d("Settings", task.result.toString())
                }
            }
    }
}