package com.reyaly.reyalyhealthtracker.screens.med

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.model.Medication
import com.reyaly.reyalyhealthtracker.storage.med.addMedication
import com.reyaly.reyalyhealthtracker.storage.med.getMedications
import com.reyaly.reyalyhealthtracker.storage.user.addUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "Med"

class MedViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(MedUiState())
    val uiState = _uiState.asStateFlow()

    private val _medState = MutableStateFlow(Medication())
    val medState = _medState.asStateFlow()

    private val medList
        get() = uiState.value.medList

    private val name
        get() = medState.value.name

    private val dose
        get() = medState.value.dose

    private val time
        get() = medState.value.time

    fun onNameChange(newValue: String) {
        _medState.value = medState.value.copy(name = newValue)
        _uiState.value = uiState.value.copy(nameError = null)
    }

    fun onDoseChange(newValue: String) {
        _medState.value = medState.value.copy(dose = newValue)
        _uiState.value = uiState.value.copy(doseError = null)
    }

    fun onTimeChange(newValue: String) {
        _medState.value = medState.value.copy(time = newValue)
        _uiState.value = uiState.value.copy(timeError = null)
    }

    private val blankMessage = "Field cannot be blank"
    private fun validateMed(): Boolean {
        Log.d(TAG, "name = $name")
        Log.d(TAG, "dose = $dose")
        Log.d(TAG, "time = $time")

        var invalidCount = 0
        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(nameError = blankMessage)
            invalidCount++
        }
        if (dose.isBlank()) {
            _uiState.value = _uiState.value.copy(doseError = blankMessage)
            invalidCount++
        }
        if (time.isBlank()) {
            _uiState.value = _uiState.value.copy(timeError = blankMessage)
            invalidCount++
        }

        return invalidCount == 0
    }

    suspend fun onAddNewMed() {
        val firebaseUser = auth.currentUser!!
        val med = Medication(name = name.lowercase(), dose = dose, time = time)
        if (validateMed()) {
            try {
                val response = addMedication(firebaseUser.uid, med)
                Log.d(TAG, response)
                med.documentId = response
                medList.add(med)
                Log.d(TAG, med.toString())
                Log.d(TAG, _uiState.value.medList.toString())
                _medState.value = medState.value.copy(name = "")
                _medState.value = medState.value.copy(dose = "")
                _medState.value = medState.value.copy(time = "")
            } catch (e: Exception) {
                Log.d(TAG, "an error occurred: $e")
            }
        }
        return
    }

    suspend fun getUsersMeds() {
        val firebaseUser = auth.currentUser!!
        _uiState.value = _uiState.value.copy(medsAreLoading = true)
        try {
            val meds = getMedications(firebaseUser.uid).toMutableList().also {
                _uiState.value = _uiState.value.copy(medsAreLoading = false)
            }
            Log.d(TAG, meds.toString())
            _uiState.value = _uiState.value.copy(medList = meds)
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
    }

}