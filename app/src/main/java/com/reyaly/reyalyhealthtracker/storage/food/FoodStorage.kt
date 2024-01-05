package com.reyaly.reyalyhealthtracker.storage.food

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "foodStorage"
val users = Firebase.firestore.collection("users")