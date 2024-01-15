package com.reyaly.reyalyhealthtracker.model

import com.google.firebase.firestore.DocumentId

data class FoodItem(
    @DocumentId
    val documentId: String = "",
    var meal: String = "",
    var name: String = "",
    var calories: String = "",
    var protein: String = "",
    var fat: String = "",
    var carbs: String = "",
    var apiId: String? = ""
)
