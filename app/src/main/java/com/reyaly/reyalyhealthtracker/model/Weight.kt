package com.reyaly.reyalyhealthtracker.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Weight(
    @DocumentId
    var documentId: String = "",
    var weight: String = "",
    var weightInKg: String = "",
)

data class WeightInfo(
    var initialWeight: String = "",
    var initialWeightInKg: String = "",
    var currWeight: String = "",
    var currWeightInKg: String = "",
    var previousWeight: String = "",
    var previousWeightInKg: String = "",
    var lowestWeight: String = "",
    var lowestWeightInKg: String = "",
    var highestWeight: String = "",
    var highestWeightInKg: String = "",
    var goalWeight: String = "",
    var goalWeightInKg: String = "",
    var weightGoals: String = "",
)

data class HistoricalWeight(
    val weight: String = "",
    val weightInKg: String = "",
)