package com.reyaly.reyalyhealthtracker.screens.dashboard

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.model.User
import com.reyaly.reyalyhealthtracker.storage.user.findUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.format.DateTimeFormatter

class DashboardViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    var user: User? = null

    suspend fun getUser() {
        val firebaseUser = auth.currentUser!!
        user = findUser(firebaseUser.uid)
    }

    suspend fun getCalsConsumed() {
//        val formattedDateStr = _uiState.value.dateChoice.format(formatter)
    }

    suspend fun getCalsBurned() {
//        val formattedDateStr = _uiState.value.dateChoice.format(formatter)
    }
}