package com.reyaly.reyalyhealthtracker.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.model.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    fun onDateChange(direction: String) {
        if (direction == "forward") {
            _uiState.value = _uiState.value.copy(dateChoice = _uiState.value.dateChoice.plusDays(1))
        }
        if (direction == "back") {
            _uiState.value = _uiState.value.copy(dateChoice = _uiState.value.dateChoice.minusDays(1))
        }
    }
}