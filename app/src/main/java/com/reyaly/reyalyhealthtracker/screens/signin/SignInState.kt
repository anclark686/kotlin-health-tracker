package com.reyaly.reyalyhealthtracker.screens.signin

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val isDeleteSuccessful: Boolean = false,
    val signInError: String? = null
)
