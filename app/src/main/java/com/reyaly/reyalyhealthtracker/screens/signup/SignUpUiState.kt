package com.reyaly.reyalyhealthtracker.screens.signup

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val repeatPasswordError: String? = null,
    val success: Boolean = false
)
