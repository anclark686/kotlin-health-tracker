package com.reyaly.reyalyhealthtracker.screens.emailandpw

data class EmailAndPwUiState (
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val success: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val isChangePwSuccessful: Boolean = false,
)