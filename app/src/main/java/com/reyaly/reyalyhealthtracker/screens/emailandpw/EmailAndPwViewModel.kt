package com.reyaly.reyalyhealthtracker.screens.emailandpw

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.ext.isValidEmail
import com.reyaly.reyalyhealthtracker.model.service.AccountService
import com.reyaly.reyalyhealthtracker.model.service.LogService
import com.reyaly.reyalyhealthtracker.screens.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EmailAndPwViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(EmailAndPwUiState())
    val uiState = _uiState.asStateFlow()

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    var loginError by mutableStateOf("")

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
        _uiState.value = _uiState.value.copy(emailError = null)
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(password = newValue)
        _uiState.value = _uiState.value.copy(passwordError = null)
    }

    fun onSignInClick(onSuccess: () -> Unit) {

        Log.d("Login", email.toString())
        Log.d("Login", password.toString())
        if (!email.isValidEmail()) {
            Log.d("Login", "Please insert a valid email.")
            _uiState.value = _uiState.value.copy(emailError = "Please insert a valid email.")
            return
        }

        if (password.isBlank()) {
            Log.d("Login", "Password cannot be empty.")
            _uiState.value = _uiState.value.copy(passwordError = "Password cannot be empty.")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val user = auth.currentUser
                    Log.d("Login", "Success")
                    Log.d("Login", auth.currentUser.toString())
                    onSuccess()
                } else {
                    // Login failed
                    Log.d("Login", "signInWithEmail:failure")
                    Log.d("Login", task.exception.toString())
                    val error = task.exception?.localizedMessage
                    Log.d("Login", error.toString())
                    if (error == "The supplied auth credential is incorrect, malformed or has expired.") {
                        loginError = "The username and password combination is incorrect"
                    }
                }
            }
        return
    }

    fun onForgotPasswordClick(onForgotPw: () -> Unit) {
        if (!email.isValidEmail()) {
            Log.d("Login", "Please insert a valid email.")
            _uiState.value = _uiState.value.copy(emailError = "Please insert a valid email.")
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "Email sent.")
                    onForgotPw()
                }
            }

        return
    }
}