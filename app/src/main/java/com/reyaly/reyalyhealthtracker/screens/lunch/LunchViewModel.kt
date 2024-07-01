package com.reyaly.reyalyhealthtracker.screens.lunch

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LunchViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(LunchUiState())
    val uiState = _uiState.asStateFlow()

    private val name
        get() = uiState.value.name

    private val calories
        get() = uiState.value.calories

    private val protein
        get() = uiState.value.protein

    private val fat
        get() = uiState.value.fat

    private val carbs
        get() = uiState.value.carbs

    private val quantity
        get() = uiState.value.quantity
}