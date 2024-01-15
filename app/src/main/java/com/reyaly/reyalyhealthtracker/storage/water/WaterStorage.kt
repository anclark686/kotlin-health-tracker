package com.reyaly.reyalyhealthtracker.storage.water

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "waterStorage"
val users = Firebase.firestore.collection("users")

suspend fun addWater(uid: String, amount: Int) {

}