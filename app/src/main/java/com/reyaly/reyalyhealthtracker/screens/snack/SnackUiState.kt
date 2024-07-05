package com.reyaly.reyalyhealthtracker.screens.snack

import com.reyaly.reyalyhealthtracker.model.FoodItem


data class SnackUiState(
    val foodList: MutableList<FoodItem> = mutableListOf(),
    val foodsAreLoading: Boolean = false,

    val nameError: String? = null,
    val caloriesError: String? = null,
    val proteinError: String? = null,
    val fatError: String? = null,
    val carbsError: String? = null,
    val quantityError: String? = null
)
