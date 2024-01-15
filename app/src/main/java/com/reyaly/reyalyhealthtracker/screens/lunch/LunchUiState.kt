package com.reyaly.reyalyhealthtracker.screens.lunch

data class LunchUiState(
    val name: String = "",
    val calories: String = "",
    val protein: String = "",
    val fat: String = "",
    val carbs: String = "",
    val quantity: Int = 0,
    val apiId: String? = null,

    val nameError: String? = null,
    val caloriesError: String? = null,
    val proteinError: String? = null,
    val fatError: String? = null,
    val carbsError: String? = null,
    val quantityError: String? = null
)
