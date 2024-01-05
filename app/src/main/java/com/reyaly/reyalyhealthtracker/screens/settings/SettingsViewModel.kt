package com.reyaly.reyalyhealthtracker.screens.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.common.ext.isValidPassword
import com.reyaly.reyalyhealthtracker.common.ext.passwordMatches
import com.reyaly.reyalyhealthtracker.model.User
import com.reyaly.reyalyhealthtracker.screens.emailandpw.EmailAndPwUiState
import com.reyaly.reyalyhealthtracker.screens.intake.IntakeUiState
import com.reyaly.reyalyhealthtracker.storage.user.deleteUserData
import com.reyaly.reyalyhealthtracker.storage.user.findUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.Period

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun getUser(): Boolean {
        val firebaseUser = auth.currentUser!!
        _uiState.value = _uiState.value.copy(userIsLoading = true)
        val user = findUser(firebaseUser.uid).also {
            _uiState.value = _uiState.value.copy(userIsLoading = false)
        }
        if (user != null) {
            _uiState.value = _uiState.value.copy(user = user)
            return true
        }
        return false
    }

    fun getAge(user: User): String {
        val (month, day, year) = user.birthday!!.split("/")

        val age = Period.between(
            LocalDate.of(year.toInt(), month.toInt(), day.toInt()),
            LocalDate.now()
        ).years

        return age.toString()
    }

    fun onSignOut() {
        auth.signOut()
    }

    suspend fun deleteAccount(onSignInRedirect: () -> Unit, onReturnHome: () -> Unit) {
        val user = auth.currentUser!!
        try {
            user.delete().await().also { deleteUserData(user.uid) }.also { onReturnHome() }
        } catch (e: Exception) {
            Log.d("settings", "an error occurred: ${e.localizedMessage}")
            onSignInRedirect()
        }
    }
}