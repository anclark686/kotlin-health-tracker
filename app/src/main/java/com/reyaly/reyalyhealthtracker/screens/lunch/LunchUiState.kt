package com.reyaly.reyalyhealthtracker.screens.lunch

import com.reyaly.reyalyhealthtracker.model.FoodItem

data class LunchUiState(
    val foodList: MutableList<FoodItem> = mutableListOf(),
    val foodsAreLoading: Boolean = false,

    val nameError: String? = null,
    val caloriesError: String? = null,
    val proteinError: String? = null,
    val fatError: String? = null,
    val carbsError: String? = null,
    val quantityError: String? = null
)
