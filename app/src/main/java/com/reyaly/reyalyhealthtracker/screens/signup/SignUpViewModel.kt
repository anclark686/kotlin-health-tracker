package com.reyaly.reyalyhealthtracker.screens.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.common.ext.isValidEmail
import com.reyaly.reyalyhealthtracker.common.ext.isValidPassword
import com.reyaly.reyalyhealthtracker.common.ext.passwordMatches
import com.reyaly.reyalyhealthtracker.screens.emailandpw.EmailAndPwUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val name
        get() = uiState.value.name
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(name = newValue)
        _uiState.value = _uiState.value.copy(nameError = null)
    }

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
        _uiState.value = _uiState.value.copy(emailError = null)
    }

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


    fun onSignUpClick(onSuccess: () -> Unit) {
        Log.d("signUp", "Name = $name")
        Log.d("signUp", "email = $email")
        Log.d("signUp", "pw = $password")
        Log.d("signUp", "repeat pw = ${_uiState.value.repeatPassword}")

        if (name == "") {
            Log.d("signUp", "Name cannot be blank.")
            _uiState.value = _uiState.value.copy(nameError = "Name cannot be blank.")
            return
        }

        if (!email.isValidEmail()) {
            Log.d("signUp", "Please insert a valid email.")
            _uiState.value = _uiState.value.copy(emailError = "Please insert a valid email.")
            return
        }

        if (!password.isValidPassword()) {
            Log.d("signUp", "Password cannot be empty.")
            _uiState.value = _uiState.value.copy(passwordError = "Your password should have at least six digits and include one digit, one lower case letter and one upper case letter.")
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            Log.d("signUp", "Password cannot be empty.")
            _uiState.value = _uiState.value.copy(repeatPasswordError = "Passwords do not match.")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    val user = auth.currentUser
                    Log.d("signUp", "Success")
                    Log.d("signUp", auth.currentUser.toString())
                    onSuccess()
                } else {
                    // Registration failed
                    Log.d("signUp", "createUserWithEmail:failure")
                    Log.d("signUp", task.exception.toString())
                    val error = task.exception?.cause
                    Log.d("signUp", error.toString())
//                    try {
//                        throw task.getException();
//                    } catch(FirebaseAuthWeakPasswordException e) {
//                        mTxtPassword.setError(getString(R.string.error_weak_password));
//                        mTxtPassword.requestFocus();
//                    } catch(FirebaseAuthInvalidCredentialsException e) {
//                        mTxtEmail.setError(getString(R.string.error_invalid_email));
//                        mTxtEmail.requestFocus();
//                    } catch(FirebaseAuthUserCollisionException e) {
//                        mTxtEmail.setError(getString(R.string.error_user_exists));
//                        mTxtEmail.requestFocus();
//                    } catch(Exception e) {
//                        Log.e(TAG, e.getMessage());
//                    }
                }
            }
        return
    }
}