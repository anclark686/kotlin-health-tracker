package com.reyaly.reyalyhealthtracker.screens.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.proto.TargetGlobal
import com.reyaly.reyalyhealthtracker.screens.settings.SettingsUiState
import com.reyaly.reyalyhealthtracker.storage.user.deleteUserData
import com.reyaly.reyalyhealthtracker.storage.user.findUser
import com.reyaly.reyalyhealthtracker.storage.user.updateUserLoginTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "signIn"
class SignInViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private suspend fun deleteAccountFinal() {
        val user = auth.currentUser!!

        user.delete().await().also { deleteUserData(user.uid) }
        Log.d(TAG, "user deleted")
    }

    suspend fun onSignInResult(result: SignInResult, onExistingUser: () -> Unit, onNewUser: () -> Unit, modify: String? = null) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }

        if (modify == "delete") {
            deleteAccountFinal()
            onExistingUser()
        } else {
            if (result.data != null) {
                val firebaseUser = auth.currentUser!!
                if (findUser(firebaseUser.uid) != null) {
                    Log.d(TAG, findUser(firebaseUser.uid).toString())
                    onExistingUser()
                    updateUserLoginTime(firebaseUser.uid)
                } else {
                    onNewUser()
                }
            }
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }


}