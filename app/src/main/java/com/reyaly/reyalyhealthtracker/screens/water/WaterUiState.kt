package com.reyaly.reyalyhealthtracker.screens.water

import com.reyaly.reyalyhealthtracker.model.WaterInfo

data class WaterUiState(
    val valuesAreLoading: Boolean = false,
    val ozError: String? = null,
    val cupsError: String? = null,

    val recommended: String = "",
    val totalOunces: String = "",
    val totalCups: String = "",
    val ouncesRemaining: String = "",
    val percentage: String? = "",

    val historicalData: HashMap<String, WaterInfo> = hashMapOf(),
    val historicalDates: List<String> = listOf(),
    val historicalDate: String? = "",
    val historicalWaterInOz: String = "",
    val historicalWaterInCups: String = "",
)
