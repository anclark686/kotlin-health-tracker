package com.reyaly.reyalyhealthtracker.screens.dashboard

import com.reyaly.reyalyhealthtracker.model.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DashboardUiState(
    val dateObj: Date? = null,
    val calsConsumed: String = "",
    val calsBurned: String = "",
)
