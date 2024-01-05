package com.reyaly.reyalyhealthtracker.storage.exercise

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "exerciseStorage"
val users = Firebase.firestore.collection("users")