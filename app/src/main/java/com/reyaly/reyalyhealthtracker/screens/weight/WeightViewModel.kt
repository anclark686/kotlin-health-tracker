package com.reyaly.reyalyhealthtracker.screens.weight

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.helpers.convertWeightToKg
import com.reyaly.reyalyhealthtracker.model.Weight
import com.reyaly.reyalyhealthtracker.model.WeightInfo
import com.reyaly.reyalyhealthtracker.storage.weight.addWeightToDates
import com.reyaly.reyalyhealthtracker.storage.weight.addWeightToMainDetails
import com.reyaly.reyalyhealthtracker.storage.weight.getAllHistoricalWeightData
import com.reyaly.reyalyhealthtracker.storage.weight.getPreviousWeightData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

class WeightViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(WeightUiState())
    val uiState = _uiState.asStateFlow()

    private val _weightState = MutableStateFlow(Weight())
    val weightState = _weightState.asStateFlow()

    private val weight
        get() = weightState.value.weight

    private val weightInKg
        get() = weightState.value.weightInKg

    private val blankMessage = "Weight cannot be blank"
    private val typeErrorMessage = "Weight must be a number"

    fun onWeightChange(newValue: String) {
        _weightState.value = weightState.value.copy(weight = newValue)
        _uiState.value = uiState.value.copy(weightError = null)
    }

    private fun validateWeight(): Boolean {
        var invalidCount = 0

        if (weight.isBlank()) {
            _uiState.value = _uiState.value.copy(weightError = blankMessage)
            invalidCount++
        }

        try {
            _weightState.value = weightState.value.copy(weightInKg = convertWeightToKg(weight))
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(weightError = typeErrorMessage)
            invalidCount++
        }

        return invalidCount == 0
    }

    suspend fun onAddNewWeight() {
        val firebaseUser = auth.currentUser!!
        val date = LocalDate.now()

        if (validateWeight()) {
            try {
                val response = addWeightToDates(firebaseUser.uid, weight, date)
                Log.d("weight", response)
                try {
                    addWeightToMainDetails(firebaseUser.uid, weight)
                    Log.d("weight", "looks like it worked?")
                } catch (e: Exception) {
                    Log.d("weight", "an error occurred: $e")
                }
                _weightState.value = weightState.value.copy(weight = "")
            } catch (e: Exception) {
                Log.d("weight", "an error occurred: $e")
            }
        }
    }

    private fun getScoreDifferenceAndLeft(weightInfo: WeightInfo): HashMap<String, String> {
        var score = ""
        var difference = ""
        var left = ""

        val current = weightInfo.currWeight.toFloat()
        val goal = weightInfo.goalWeight.toFloat()
        val initial = weightInfo.initialWeight.toFloat()

        when {
            weightInfo.weightGoals == "Maintain Weight" -> {
                if (initial == current && current == goal) {
                    // they've maintained weight and hit their goal
                    score = "100%"
                    difference = "0 lbs"
                    left = "0 lbs"
                } else if (current > initial) {
                    // they've gained weight
                    score = "${round((goal / current) * 100)}%"
                    difference = "+${round(current - initial)} lbs"
                    left = "${round(current - goal)} lbs"
                } else {
                    // they've lost weight
                    score = "${round((current / goal) * 100)}%"
                    difference = "-${round(initial - current)} lbs"
                    left = "${round(goal - current)} lbs"
                }
            }
            "Loss" in weightInfo.weightGoals -> {
                if (current == initial) {
                    // they've maintained weight
                    score = "${round((goal / current) * 100).toInt()}%"
                    difference = "0 lbs"
                    left = "${round(initial - goal).toInt()} lbs"
                } else if (goal == current || goal > current) {
                    // they've hit their goal
                    score = "100%"
                    difference = "-${round(initial - goal).toInt()} lbs"
                    left = "0 lbs"
                } else if (current > initial) {
                    // they've gained weight when they shouldn't
                    score = "${round((goal / current) * 100).toInt()}%"
                    difference = "+${round(current - initial).toInt()} lbs"
                    left = "${round(current - goal).toInt()} lbs"
                } else {
                    // they've lost weight when they should
                    score = "${round((goal / current) * 100).toInt()}%"
                    difference = "-${round(initial - current).toInt()} lbs"
                    left = "${round(current - goal).toInt()} lbs"
                }
            }
            "Gain" in weightInfo.weightGoals -> {
                if (current == initial) {
                    // they've maintained weight
                    score = "0%"
                    difference = "0 lbs"
                    left = "${round(goal - initial).toInt()} lbs"
                } else if (goal == current || current > goal) {
                    // they've hit their goal
                    score = "100%"
                    difference = "+${round(goal - initial).toInt()} lbs"
                    left = "0 lbs"
                } else if (current > initial) {
                    // they've gained weight when they should
                    score = "${round((current / goal) * 100).toInt()}%"
                    difference = "+${round(current - initial).toInt()} lbs"
                    left = "${round(goal - current).toInt()} lbs"
                } else {
                    // they've lost weight when they shouldn't
                    score = "${round((current / goal) * 100).toInt()}%"
                    difference = "-${round(initial - current).toInt()} lbs"
                    left = "${round(goal - current).toInt()} lbs"
                }
            }
        }

       return hashMapOf(
           "score" to score,
           "difference" to difference,
           "left" to left,
       )
    }

    suspend fun getWeightGoals() {
        val firebaseUser = auth.currentUser!!
        _uiState.value = _uiState.value.copy(valuesAreLoading = true)

        try {
            val weightInfo = getPreviousWeightData(firebaseUser.uid).also{
                _uiState.value = _uiState.value.copy(valuesAreLoading = false)
            }
            if (weightInfo != null) {
                Log.d("weightViewModel", weightInfo.toString())
                val goalData = getScoreDifferenceAndLeft(weightInfo)

                Log.d("weightViewModel", goalData.toString())
                _uiState.value = _uiState.value.copy(
                    initialWeight = "${weightInfo.initialWeight} lbs",
                    currentWeight = "${weightInfo.currWeight} lbs",
                    goalWeight = "${weightInfo.goalWeight} lbs",
                    score = goalData["score"],
                    difference = goalData["difference"],
                    left = goalData["left"],
                )
            }
        } catch (e: Exception) {
            Log.d("weight", "an error occurred: $e")
        }
    }

    suspend fun getWeightStats() {
        val firebaseUser = auth.currentUser!!
        _uiState.value = _uiState.value.copy(valuesAreLoading = true)

        try {
            val weightInfo = getPreviousWeightData(firebaseUser.uid).also{
                _uiState.value = _uiState.value.copy(valuesAreLoading = false)
            }

            if (weightInfo != null) {
                Log.d("weightViewModel", weightInfo.toString())

                val goalData = getScoreDifferenceAndLeft(weightInfo)

                _uiState.value = _uiState.value.copy(
                    initialWeight = "${weightInfo.initialWeight} lbs",
                    currentWeight = "${weightInfo.currWeight} lbs",
                    goalWeight = "${weightInfo.goalWeight} lbs",
                    highestWeight = "${weightInfo.highestWeight} lbs",
                    lowestWeight = "${weightInfo.lowestWeight} lbs",
                    weightDifference = goalData["difference"],
                )
            }
        } catch (e: Exception) {
            Log.d("weight", "an error occurred: $e")
        }
    }

    suspend fun getHistoricalData() {
        val firebaseUser = auth.currentUser!!
        _uiState.value = _uiState.value.copy(valuesAreLoading = true)

        try {
            val response = getAllHistoricalWeightData(firebaseUser.uid).also{
                _uiState.value = _uiState.value.copy(valuesAreLoading = false)
            }
            _uiState.value = _uiState.value.copy(historicalData = response)
            _uiState.value = _uiState.value.copy(historicalDates = response.keys)
        } catch (e: Exception) {
            Log.d("weight", "an error occurred: $e")
        }
    }

    fun changeHistoricalWeightValue(date: String) {
        _uiState.value = _uiState.value.copy(historicalDate = date)
        _uiState.value = _uiState.value.copy(historicalWeight = _uiState.value.historicalData[date]?.weight)
        _uiState.value = _uiState.value.copy(historicalWeightInKg = _uiState.value.historicalData[date]?.weightInKg)
        Log.d("weight", date)
        Log.d("weight", _uiState.value.historicalData[date].toString())
    }
}