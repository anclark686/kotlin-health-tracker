package com.reyaly.reyalyhealthtracker.screens.lunch

import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.model.FoodStat
import com.reyaly.reyalyhealthtracker.screens.food.FoodItems

data class LunchUiState(
    val foodList: MutableList<FoodItem> = mutableListOf(),
    val existingFoodObject: FoodItems = FoodItems(
        breakfast = mutableListOf(),
        lunch = mutableListOf(),
        dinner = mutableListOf(),
        snacks = mutableListOf()
    ),
    val foodsAreLoading: Boolean = false,
    val foodStats: FoodStat = FoodStat(
        unique = 0,
        total = 0,
        calories = 0,
        protein = 0,
        fat = 0,
        carbs = 0,
    ),

    val nameError: String? = null,
    val caloriesError: String? = null,
    val proteinError: String? = null,
    val fatError: String? = null,
    val carbsError: String? = null,
    val quantityError: String? = null
)
