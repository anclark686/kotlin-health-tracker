package com.reyaly.reyalyhealthtracker.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Weight(
    @DocumentId
    val documentId: String = "",
    var pounds: Int = 0,
    var kg: Int = 0,
    var timestamp: Timestamp? = null
)
