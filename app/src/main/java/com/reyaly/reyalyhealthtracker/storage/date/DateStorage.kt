package com.reyaly.reyalyhealthtracker.storage.date

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.model.Date
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "dateStorage"
private const val NAME = "date"
private const val COLLECTION = "dates"
val users = Firebase.firestore.collection("users")

suspend fun addDate(uid: String, dateStr: String) {
    val dateObj = Date()
    Log.d(TAG, "what about here?")
    users
        .document(uid)
        .collection(COLLECTION)
        .document(dateStr)
        .set(dateObj)
        .await()
}

suspend fun checkIfDateExists(uid: String, date: LocalDate) {
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    val formattedDateStr = date.format(formatter)
    try {
        val querySnapshot = users
            .document(uid)
            .collection(COLLECTION)
            .document(formattedDateStr)
            .get()
            .await()
        Log.d(TAG, "you get here?")
        Log.d(TAG, uid)
        Log.d(TAG, formattedDateStr)
        Log.d(TAG, querySnapshot.toString())
        if (!querySnapshot.exists()) {
            addDate(uid, formattedDateStr)
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d(TAG, e.toString())
    }

    suspend fun getMeals(uid: String, date: LocalDate, meal: String) {

    }
}