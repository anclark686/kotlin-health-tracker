package com.reyaly.reyalyhealthtracker.storage.water

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.model.WaterInfo
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "waterStorage"
private const val DATESCOLLECTION = "dates"
val users = Firebase.firestore.collection("users")

suspend fun getHistoricalWater(uid: String): HashMap<String, WaterInfo> {
    val data = hashMapOf<String, WaterInfo>()

    val response = users
        .document(uid)
        .collection(DATESCOLLECTION)
        .get()
        .await()

    if (response != null) {
        for (document in response) {
            Log.d(TAG, "${document.id} => ${document.data}")
            val waterInfo = document.toObject(WaterInfo::class.java)

            data[document.id] = waterInfo
        }
        Log.d(TAG, data.toString())
    }

    return data
}

suspend fun getWaterByDate(uid: String, date: LocalDate): WaterInfo? {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    var waterInfo: WaterInfo? = null

    val response = users
        .document(uid)
        .collection(DATESCOLLECTION)
        .document(date.format(formatter))
        .get()
        .await()

    if (response != null) {
        Log.d(TAG, response.data.toString())
        Log.d(TAG, (response.data?.contains("waterInOunces") == true).toString())
        waterInfo = response.toObject(WaterInfo::class.java)
    }

    return waterInfo
}

suspend fun addWater(uid: String, oz: String, cups: String, date: LocalDate) {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

    val dataRef = users
        .document(uid)
        .collection(DATESCOLLECTION)
        .document(date.format(formatter))

    try {
        val response = getWaterByDate(uid, date)

        if (response != null) {
            Log.d(TAG, "wassup")
            Log.d(TAG, response.toString())
            if (response.waterInOunces == 0 && response.waterInCups == 0) {
                val data = hashMapOf<String, Any>(
                    "waterInOunces" to oz.toInt(),
                    "waterInCups" to cups.toInt(),
                )

                dataRef.update(data).await()
            } else {
                val data = hashMapOf<String, Any>(
                    "waterInOunces" to FieldValue.increment(oz.toDouble()),
                    "waterInCups" to FieldValue.increment(cups.toDouble()),
                )
                dataRef.update(data).await()
            }
        } else {
            val data = hashMapOf<String, Any>(
                "waterInOunces" to oz.toInt(),
                "waterInCups" to cups.toInt(),
            )
            Log.d(TAG, data.toString())

            dataRef.set(data).await()
        }
    } catch (e: Exception) {
        Log.d(TAG, "an error occurred: $e")
    }
}