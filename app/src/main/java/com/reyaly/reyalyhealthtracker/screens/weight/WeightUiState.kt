package com.reyaly.reyalyhealthtracker.screens.weight

import com.reyaly.reyalyhealthtracker.model.HistoricalWeight
import com.reyaly.reyalyhealthtracker.model.Medication

data class WeightUiState(
    val valuesAreLoading: Boolean = false,
    val weightError: String? = null,

    val initialWeight: String? = "",
    val currentWeight: String? = "",
    val goalWeight: String? = "",
    val score: String? = "",
    val difference: String? = "",
    val left: String? = "",

    val highestWeight: String? = "",
    val lowestWeight: String? = "",
    val weightDifference: String? = "",
    val bmi: String? = "",
    val bodyFat: String? = "",
    val idealWeight: String? = "",

    val historicalData: HashMap<String, HistoricalWeight> = hashMapOf(),
    val historicalDates: MutableSet<String> = mutableSetOf(),
    val historicalDate: String? = "",
    val historicalWeight: String? = "",
    val historicalWeightInKg: String? = "",
)
