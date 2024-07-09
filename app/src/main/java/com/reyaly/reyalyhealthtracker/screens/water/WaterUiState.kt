package com.reyaly.reyalyhealthtracker.screens.water

import com.reyaly.reyalyhealthtracker.model.WaterInfo

data class WaterUiState(
    val valuesAreLoading: Boolean = false,
    val ozError: String? = null,
    val cupsError: String? = null,

    val recommended: Int? = 0,
    val totalOunces: Int? = 0,
    val totalCups: Int? = 0,
    val ouncesRemaining: Int? = 0,
    val percentage: String? = "",

    val historicalData: HashMap<String, WaterInfo> = hashMapOf(),
    val historicalDates: List<String> = listOf(),
    val historicalDate: String? = "",
    val historicalWaterInOz: Int? = 0,
    val historicalWaterInCups: Int? = 0,
)
