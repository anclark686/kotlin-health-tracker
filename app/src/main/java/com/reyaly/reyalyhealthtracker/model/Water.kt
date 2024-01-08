package com.reyaly.reyalyhealthtracker.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Water(
    @DocumentId
    val documentId: String = "",
    var timestamp: Timestamp? = null,
    var ounces: Int = 0
)
