package com.reyaly.reyalyhealthtracker.storage.food

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.model.Medication
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "foodStorage"
private const val FOODCOLLECTION = "foods"
private const val DATESCOLLECTION = "dates"
val users = Firebase.firestore.collection("users")

suspend fun addFoodToUsersFoods(uid: String, foodItem: FoodItem): String {
    // need to add check to see if item is already in foods
    val dataRef = users
        .document(uid)
        .collection(FOODCOLLECTION)
        .document(foodItem.name)

    dataRef.set(foodItem).await()
    return dataRef.id
}

suspend fun addFoodToDailyMeals(uid: String, foodItem: FoodItem, date: LocalDate): String {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

    val dataRef = users
        .document(uid)
        .collection(DATESCOLLECTION)
        .document(date.format(formatter))
        .collection(foodItem.meal.lowercase())
        .document(foodItem.name.lowercase())

    dataRef.set(foodItem).await()
    return dataRef.id
}

suspend fun findFoodInFoods(uid: String, foodName: String): FoodItem? {
    val food = users
        .document(uid)
        .collection(FOODCOLLECTION)
        .document(foodName.lowercase())
        .get()
        .await()

    if (food != null) {
        return food.toObject(FoodItem::class.java)
    }
    return null
}

suspend fun findMealsInDates(uid: String, meal: String, date: LocalDate): List<FoodItem> {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

    val foods = users
        .document(uid)
        .collection(DATESCOLLECTION)
        .document(date.format(formatter))
        .collection(meal.lowercase())
        .get()
        .await()

    return foods.map { food ->  food.toObject(FoodItem::class.java) }
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