package com.reyaly.reyalyhealthtracker.viewmodels.user

import android.app.Activity.RESULT_OK
import androidx.lifecycle.ViewModel
import com.reyaly.reyalyhealthtracker.data.state.user.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.callback.Callback
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.R

class UserViewModel : ViewModel() {

    private val TAG = "UserModel"

    var userUiState by mutableStateOf(UserUiState())
        private set

    private lateinit var context: Context

    fun setContext(activityContext: Context) {
        context = activityContext
    }



}


//class UserViewModel : ViewModel() {
////    private val _userUiState = MutableStateFlow(UserUiState())
////    val userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()
//
////    private lateinit var account: Auth0
////    var userIsAuthenticated = false
//
//
//    var appJustLaunched by mutableStateOf(true)
//    var userIsAuthenticated by mutableStateOf(false)
//
//    var userUiState by mutableStateOf(UserUiState())
//
//    private val TAG = "MainViewModel"
//    private lateinit var account: Auth0
//    private lateinit var context: Context
//
//
//    fun setContext(activityContext: Context) {
//        context = activityContext
//        account = Auth0(
//            context.getString(R.string.com_auth0_client_id),
//            context.getString(R.string.com_auth0_domain)
//        )
//    }
//
//
//    fun login() {
//        WebAuthProvider
//            .login(account)
//            .withScheme(context.getString(R.string.com_auth0_scheme))
//            .start(context, object : Callback<Credentials, AuthenticationException> {
//
//                override fun onFailure(error: AuthenticationException) {
//                    // The user either pressed the “Cancel” button
//                    // on the Universal Login screen or something
//                    // unusual happened.
//                    Log.e(TAG, "Error occurred in login(): $error")
//                }
//
//                override fun onSuccess(result: Credentials) {
//                    // The user successfully logged in.
//                    val idToken = result.idToken
//
//                    // TODO: 🚨 REMOVE BEFORE GOING TO PRODUCTION!
//                    Log.d(TAG, "ID token: $idToken")
//
//                    userUiState = UserUiState(idToken)
//
//                    userIsAuthenticated = true
//                    appJustLaunched = false
//                }
//
//            })
//    }
//
//
//    fun logout() {
//        WebAuthProvider
//            .logout(account)
//            .withScheme(context.getString(R.string.com_auth0_scheme))
//            .start(context, object : Callback<Void?, AuthenticationException> {
//
//                override fun onFailure(error: AuthenticationException) {
//                    // For some reason, logout failed.
//                    Log.e(TAG, "Error occurred in logout(): $error")
//                }
//
//                override fun onSuccess(result: Void?) {
//                    // The user successfully logged out.
//                    userUiState = UserUiState()
//                    userIsAuthenticated = false
//                }
//
//            })
//    }
//
//}