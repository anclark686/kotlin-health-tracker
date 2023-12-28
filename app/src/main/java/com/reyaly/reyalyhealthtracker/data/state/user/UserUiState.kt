package com.reyaly.reyalyhealthtracker.data.state.user


import android.util.Log
import com.auth0.android.jwt.JWT


data class UserUiState(
    val email: String = "",
    val password: String = ""
)