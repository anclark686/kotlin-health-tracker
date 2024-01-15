package com.reyaly.reyalyhealthtracker.storage.food

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.model.FoodItem
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "foodStorage"
private const val COLLECTION = "foods"
val users = Firebase.firestore.collection("users")

suspend fun addFoodToDB(uid: String, foodItem: FoodItem) {
    users
        .document(uid)
        .collection(COLLECTION)
        .document(foodItem.name)
        .set(foodItem)
        .await()
}

suspend fun findFood(uid: String, foodName: String): FoodItem? {
    val food = users
        .document(uid)
        .collection(COLLECTION)
        .document(foodName)
        .get()
        .await()

    if (food != null) {
        return food.toObject(FoodItem::class.java)
    }
    return null
}

suspend fun updateMeal(
    uid: String,
    foodId: String,
    meal: String,
    date: LocalDate
) {
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    val formattedDateStr = date.format(formatter)
    users
        .document(uid)
        .collection("dates")
        .document(formattedDateStr)
        .update(meal, foodId)
        .await()
}