package com.reyaly.reyalyhealthtracker.model

import com.google.firebase.firestore.DocumentId

data class Exercise(
    @DocumentId
    val documentId: String = "",
    val name: String = "",
    val calsBurned: String = "",
    val lengthTime: String = "",
    val intensity: String = "",
    val workoutType: String = ""
)
