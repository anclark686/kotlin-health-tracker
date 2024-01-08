package com.reyaly.reyalyhealthtracker.storage.weight

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "weightStorage"
private const val NAME = "weight"
val users = Firebase.firestore.collection("users")

