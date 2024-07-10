package com.reyaly.reyalyhealthtracker.screens.emailandpw

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.common.ext.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.reyaly.reyalyhealthtracker.storage.user.findUser
import com.reyaly.reyalyhealthtracker.storage.user.updateUserLoginTime
import kotlinx.coroutines.tasks.await

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
        _uiState.value = _uiState.value.copy(
            email = newValue,
            emailError = null
        )
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            password = newValue,
            passwordError = null
        )
    }

    suspend fun onSignInClick(onSuccess: () -> Unit, onNewUser: () -> Unit = { }) {
        Log.d("Login", email)
        Log.d("Login", password)

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

        try {
            auth.signInWithEmailAndPassword(email, password)
                .await().also {
                    val firebaseUser = auth.currentUser!!

                    if (findUser(firebaseUser.uid) != null) {
                        onSuccess()
                        updateUserLoginTime(firebaseUser.uid)
                    } else {
                        onNewUser()
                    }
                }
            Log.d("Login", "Success")
            Log.d("Login", auth.currentUser.toString())
        } catch (e: Exception) {
            Log.d("Login", "an error occurred: ${e.localizedMessage}")
            Log.d("Login", "signInWithEmail:failure")
            if (e.localizedMessage == "The supplied auth credential is incorrect, malformed or has expired.") {
                loginError = "The username and password combination is incorrect"
            }
        }
        return
    }

    suspend fun onForgotPasswordClick(onForgotPw: () -> Unit) {
        if (!email.isValidEmail()) {
            Log.d("Login", "Please insert a valid email.")
            _uiState.value = _uiState.value.copy(emailError = "Please insert a valid email.")
            return
        }

        try {
            auth.sendPasswordResetEmail(email).await().also { onForgotPw() }
            Log.d("Login", "Email sent.")
        } catch (e: Exception) {
            Log.d("Login", "an error occurred: $e")
        }
        return
    }

}