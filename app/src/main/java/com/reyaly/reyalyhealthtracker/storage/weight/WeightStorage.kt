package com.reyaly.reyalyhealthtracker.storage.weight

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.helpers.convertWeightToKg
import com.reyaly.reyalyhealthtracker.model.HistoricalWeight
import com.reyaly.reyalyhealthtracker.model.User
import com.reyaly.reyalyhealthtracker.model.Weight
import com.reyaly.reyalyhealthtracker.model.WeightInfo
import com.reyaly.reyalyhealthtracker.storage.user.findUser
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "weightStorage"
private const val DATESCOLLECTION = "dates"
val users = Firebase.firestore.collection("users")

suspend fun getWeightByDate(uid: String, date: String): Weight? {
    var weight: Weight? = null

    val response = users
        .document(uid)
        .collection(DATESCOLLECTION)
        .document(date)
        .get()
        .await()

    if (response != null) {
        Log.d(TAG, response.data.toString())
        weight = response.toObject(Weight::class.java)
    }

    return weight
}

suspend fun addWeightToDates(uid: String, newWeight: String, date: String): String {
    val newWeightInKg = convertWeightToKg(newWeight)
    val data = hashMapOf<String, Any>(
        "weight" to newWeight,
        "weightInKg" to newWeightInKg,
    )

    val dataRef = users
        .document(uid)
        .collection(DATESCOLLECTION)
        .document(date)

    val existingWeight = getWeightByDate(uid, date)

    if (existingWeight != null) {
        dataRef.update(data).await()
    } else {
        dataRef.set(data).await()
    }

    return dataRef.id
}

suspend fun getPreviousWeightData(uid: String): WeightInfo? {
    try {
        val user = findUser(uid)
        if (user != null) {
            val weightInfo = WeightInfo(
                initialWeight = user.initialWeight!!,
                initialWeightInKg = user.initialWeightInKg!!,
                currWeight = user.currWeight!!,
                currWeightInKg = user.currWeightInKg!!,
                previousWeight = user.previousWeight!!,
                previousWeightInKg = user.previousWeight!!,
                lowestWeight = user.lowestWeight!!,
                lowestWeightInKg = user.lowestWeightInKg!!,
                highestWeight = user.highestWeight!!,
                highestWeightInKg = user.highestWeightInKg!!,
                goalWeight = user.goalWeight!!,
                goalWeightInKg = user.goalWeightInKg!!,
                weightGoals = user.weightGoals!!,
            )
            Log.d(TAG, weightInfo.toString())
            return weightInfo
        }
    } catch (e: Exception) {
        Log.d(TAG, "an error occurred: $e")
    }

    return null
}

suspend fun addWeightToMainDetails(uid: String, newWeight: String) {
    val newWeightInKg = convertWeightToKg(newWeight)

    val weightInfo = getPreviousWeightData(uid)

    if (weightInfo != null) {
        if (newWeight < weightInfo.lowestWeight) {
            weightInfo.lowestWeight = newWeight
            weightInfo.lowestWeightInKg = newWeightInKg
        } else if (newWeight > weightInfo.highestWeight) {
            weightInfo.highestWeight = newWeight
            weightInfo.highestWeightInKg = newWeightInKg
        }

        val data = hashMapOf<String, Any>(
            "currWeight" to newWeight,
            "currWeightInKg" to newWeightInKg,
            "previousWeight" to weightInfo.currWeight,
            "previousWeightInKg" to weightInfo.currWeightInKg,
            "lowestWeight" to weightInfo.lowestWeight,
            "lowestWeightInKg" to weightInfo.lowestWeightInKg,
            "highestWeight" to weightInfo.highestWeight,
            "highestWeightInKg" to weightInfo.highestWeightInKg,
        )

        val dataRef = users.document(uid)

        dataRef.update(data).await()
    }
}

suspend fun getAllHistoricalWeightData(uid: String): HashMap<String, HistoricalWeight> {
    val data = hashMapOf<String, HistoricalWeight>()

    val response = users
        .document(uid)
        .collection(DATESCOLLECTION)
        .get()
        .await()

    if (response != null) {
        Log.d(TAG, response.toString())
        for (document in response) {
            Log.d(TAG, "${document.id} => ${document.data}")
            val historicalWeight = document.toObject(HistoricalWeight::class.java)

            data[document.id] = historicalWeight
        }
        Log.d(TAG, data.toString())
    }

    return data
}
