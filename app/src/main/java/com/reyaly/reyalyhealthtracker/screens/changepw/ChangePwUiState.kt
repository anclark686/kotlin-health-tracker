package com.reyaly.reyalyhealthtracker.screens.changepw

data class ChangePwUiState(
    val password: String = "",
    val repeatPassword: String = "",
    val passwordError: String? = null,
    val repeatPasswordError: String? = null,
)
