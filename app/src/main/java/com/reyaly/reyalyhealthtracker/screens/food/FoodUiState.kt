package com.reyaly.reyalyhealthtracker.screens.food

import com.reyaly.reyalyhealthtracker.model.FoodItem

data class FoodItems(
    val breakfast: MutableList<FoodItem>,
    val lunch: MutableList<FoodItem>,
    val dinner: MutableList<FoodItem>,
    val snacks: MutableList<FoodItem>
)