package com.reyaly.reyalyhealthtracker.screens

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class AppUiState(
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"),
    val dateChoice: LocalDate = LocalDate.now(),
)
