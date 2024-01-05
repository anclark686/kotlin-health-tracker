package com.reyaly.reyalyhealthtracker.storage.med

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.model.Medication
import kotlinx.coroutines.tasks.await

private const val TAG = "medStorage"
private const val NAME = "meds"
val users = Firebase.firestore.collection("users")

suspend fun addMedication(uid: String, med: Medication) {
    users.document(uid).collection(NAME).add(med).await()
}

suspend fun editMedication(uid: String, medId: String) {
    // TODO
}

suspend fun deleteMedication(uid: String, medId: String) {
    // TODO
}

suspend fun getMedications(uid: String): List<Medication> {
    val meds = users.document(uid).collection(NAME).get().await()
    return meds.map { med ->  med.toObject(Medication::class.java) }
}
