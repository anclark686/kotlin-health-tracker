package com.reyaly.reyalyhealthtracker.screens.settings

import com.reyaly.reyalyhealthtracker.model.User

data class SettingsUiState(
    val googleAuthError: String? = null,
    val user: User? = null,
    val userIsLoading: Boolean = false
)
