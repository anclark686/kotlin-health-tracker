package com.reyaly.reyalyhealthtracker.model

import com.google.firebase.firestore.DocumentId

data class Date(
    @DocumentId
    val documentId: String = "",
    var breakfast: List<String> = listOf(),
    var lunch: List<String> = listOf(),
    var dinner: List<String> = listOf(),
    var snacks: List<String> = listOf(),
    var workout: List<String> = listOf(),
    var meds: List<String> = listOf(),
    var waterTotal: Int = 0,
    var caloriesConsumed: Int = 0,
    var caloriesBurned: Int = 0,
    var weightInLbs: Int = 0,
    var weightInKgs: Int = 0,
)
