package com.reyaly.reyalyhealthtracker.storage.med

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.model.Medication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "medStorage"
private const val MEDS = "meds"
private const val DATES = "dates"
val users = Firebase.firestore.collection("users")

suspend fun getMedications(uid: String): List<Medication> {
    val meds = users.document(uid).collection(MEDS).get().await()

    Log.d(TAG, meds.map { med ->  med}.toString())
    Log.d(TAG, meds.map { med ->  med.toObject(Medication::class.java) }.toString())
    return meds.map { med ->  med.toObject(Medication::class.java) }
}


suspend fun addMedicationToMeds(uid: String, med: Medication): String {
    val medRef = users.document(uid).collection(MEDS).document(med.name.lowercase())

    try {
        var found = false
        val meds = getMedications(uid)

        meds.forEach { medFromDb ->
            if (medFromDb.name == med.name) {
                found = true
            }
        }

        if (!found) {
            medRef.set(med).await()
            return medRef.id
        } else {
            Log.d(TAG, "med already exists: ${med.name}")
        }
    } catch (e: Exception) {
        Log.d(TAG, "An error occurred: $e")
    }

    return ""
}

suspend fun addMedicationToDates(uid: String, med: Medication, date: String, time: String) {
    val medRef = users
        .document(uid)
        .collection(DATES)
        .document(date)
        .collection("${time}Meds")
        .document(med.name.lowercase())

    medRef.set(med).await()
}

suspend fun addMedsToDates(uid: String, meds: List<Medication>, date: String, time: String): MutableList<Medication> {
    var medData: MutableList<Medication> = mutableListOf()

    meds.forEach { med ->
        med.taken = false
        med.time = time
        medData.add(med)

        addMedicationToDates(uid, med, date, time)
    }

    return medData
}

suspend fun getMedDateInfo(uid: String, meds: List<Medication>, date: String, time: String): MutableList<Medication> {
    var medData: MutableList<Medication> = mutableListOf()

    val docs = users
        .document(uid)
        .collection(DATES)
        .document(date)
        .collection("${time}Meds")
        .get()
        .await()

    if (docs.isEmpty) {
        medData = addMedsToDates(uid, meds, date, time)
    } else {
        docs.forEach { doc ->
            medData.add(doc.toObject(Medication::class.java))
        }
    }

    Log.d(TAG, "Here we go")
    Log.d(TAG, time)
    Log.d(TAG, docs.toString())
    Log.d(TAG, docs.count().toString())

    return medData
}

suspend fun toggleMedInDB(uid: String, med: Medication, date: String, time: String) {
    val medRef = users
        .document(uid)
        .collection(DATES)
        .document(date)
        .collection("${time}Meds")
        .document(med.name)

    medRef.update("taken", med.taken).await()
}

suspend fun editMedication(uid: String, med: Medication) {
    val medRef = users.document(uid).collection(MEDS).document(med.name.lowercase())

    medRef.set(med).await()
}

suspend fun deleteMedication(uid: String, medName: String) {
    users.document(uid).collection(MEDS).document(medName)
        .delete()
        .await()
}