package com.reyaly.reyalyhealthtracker.screens.water

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.helpers.convertCupsToOz
import com.reyaly.reyalyhealthtracker.helpers.convertOzToCups
import com.reyaly.reyalyhealthtracker.model.Water
import com.reyaly.reyalyhealthtracker.storage.water.addWater
import com.reyaly.reyalyhealthtracker.storage.water.getHistoricalWater
import com.reyaly.reyalyhealthtracker.storage.water.getWaterByDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WaterViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(WaterUiState())
    val uiState = _uiState.asStateFlow()

    private val _waterState = MutableStateFlow(Water())
    val waterState = _waterState.asStateFlow()

    private val ounces
        get() = waterState.value.ounces

    private val cups
        get() = waterState.value.cups

    fun onOzChange(newValue: String) {
        _waterState.value = _waterState.value.copy(ounces = newValue)
        _uiState.value = _uiState.value.copy(ozError = null)

        _waterState.value = _waterState.value.copy(cups = convertOzToCups(newValue))
        _uiState.value = _uiState.value.copy(cupsError = null)
    }

    fun onCupsChange(newValue: String) {
        _waterState.value = _waterState.value.copy(cups = newValue)
        _uiState.value = _uiState.value.copy(cupsError = null)

        _waterState.value = _waterState.value.copy(ounces = convertCupsToOz(newValue))
        _uiState.value = _uiState.value.copy(ozError = null)
    }

    private val ozBlankMessage = "Ounces cannot be blank"
    private val typeErrorMessage = "Ounces must be a number"
    private val chooseOption = "Please select an option"

    private fun validateForm(ozOrCups: String): Boolean {
        var invalidCount = 0

        if (ounces.isBlank() && ozOrCups == "oz") {
            _uiState.value = uiState.value.copy(ozError = ozBlankMessage)
            invalidCount++
        }

        if (ounces.isNotBlank() && ozOrCups == "oz") {
            try {
                val dummy = ounces.toInt()
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(ozError = typeErrorMessage)
                invalidCount++
            }
        }

        if (cups.isBlank() && ozOrCups == "cups") {
            _uiState.value = uiState.value.copy(cupsError = chooseOption)
            invalidCount++
        }

        return invalidCount == 0
    }

    suspend fun onAddWater(cupsOrOunces: Boolean): Boolean {
        val firebaseUser = auth.currentUser!!
        val ozOrCups = if (cupsOrOunces) {"cups"} else {"oz"}

        Log.d("water", "here we are")
        Log.d("water", cupsOrOunces.toString())
        Log.d("water", ozOrCups)
        Log.d("water", ounces)
        Log.d("water", cups)

        if (validateForm(ozOrCups)) {
            try {
                addWater(firebaseUser.uid, ounces, cups, LocalDate.now())
                _waterState.value = waterState.value.copy(
                    ounces = "",
                    cups = "",
                )
                return true
            } catch (e: Exception) {
                Log.d("water", "an error occurred: $e")
            }
        }

        return false
    }

    suspend fun getHistoricalData() {
        val firebaseUser = auth.currentUser!!
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

        _uiState.value = _uiState.value.copy(valuesAreLoading = true)

        try {
            val response = getHistoricalWater(firebaseUser.uid).also {
                _uiState.value = _uiState.value.copy(valuesAreLoading = false)
            }

            _uiState.value = _uiState.value.copy(
                historicalData = response,
                historicalDates = response.keys.sortedBy {
                    LocalDate.parse(it, formatter)
                }
            )
        } catch (e: Exception) {
            Log.d("water", "an error occurred: $e")
        }
    }

    fun changeHistoricalWeightValue(date: String) {
        _uiState.value = _uiState.value.copy(
            historicalDate = date,
            historicalWaterInOz = _uiState.value.historicalData[date]?.waterInOunces,
            historicalWaterInCups = _uiState.value.historicalData[date]?.waterInCups
        )
        Log.d("water", date)
        Log.d("water", _uiState.value.historicalData[date].toString())
    }

    suspend fun getTodaysWater() {
        val firebaseUser = auth.currentUser!!
        _uiState.value = _uiState.value.copy(valuesAreLoading = true)

        try {
            val water = getWaterByDate(firebaseUser.uid, LocalDate.now()).also {
                _uiState.value = _uiState.value.copy(valuesAreLoading = false)
            }

            if (water != null) {
                _uiState.value = _uiState.value.copy(
                    totalOunces = water.waterInOunces,
                    totalCups = water.waterInCups
                )
            }
        } catch (e: Exception) {
            Log.d("water", "an error occurred: $e")
        }
    }
}