package com.reyaly.reyalyhealthtracker.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Water(
    @DocumentId
    val documentId: String = "",
    var ounces: String = "",
    var cups: String = "",
)

data class WaterInfo(
    var waterInOunces: Int =  0,
    var waterInCups: Double =  0.0,
)