package com.reyaly.reyalyhealthtracker.model

import com.google.firebase.firestore.DocumentId

data class Medication(
    @DocumentId
    var documentId: String = "",
    var name: String = "",
    var dose: String = "",
    var time: String = "",
)
