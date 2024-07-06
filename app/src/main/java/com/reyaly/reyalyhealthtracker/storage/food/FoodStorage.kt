package com.reyaly.reyalyhealthtracker.storage.food

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.screens.food.FoodItems
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "foodStorage"
private const val FOODCOLLECTION = "foods"
private const val DATESCOLLECTION = "dates"
val users = Firebase.firestore.collection("users")

suspend fun addOrEditFoodInFoods(uid: String, foodItem: FoodItem): String {
    // need to add check to see if item is already in foods
    val dataRef = users
        .document(uid)
        .collection(FOODCOLLECTION)
        .document(foodItem.name)

    dataRef.set(foodItem).await()
    return dataRef.id
}

suspend fun addOrEditFoodsInDates(uid: String, foodItem: FoodItem, date: LocalDate): String {
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

suspend fun findAllFoods(uid: String): FoodItems {
    val breakfastArr: MutableList<FoodItem> = mutableListOf()
    val lunchArr: MutableList<FoodItem> = mutableListOf()
    val dinnerArr: MutableList<FoodItem> = mutableListOf()
    val snacksArr: MutableList<FoodItem> = mutableListOf()

    val foods = users
        .document(uid)
        .collection(FOODCOLLECTION)
        .get()
        .await()

    val foodsArray = foods.map { food -> food.toObject(FoodItem::class.java) }
    Log.d("foodStorage", foodsArray.toString())
    foodsArray.forEach { food ->
        Log.d("foodStorage", food.toString())
        when {
            food.meal == "breakfast" -> breakfastArr.add(food)
            food.meal == "lunch" -> lunchArr.add(food)
            food.meal == "dinner" -> dinnerArr.add(food)
            food.meal == "snacks" -> snacksArr.add(food)
        }
    }
    Log.d("foodStorage", breakfastArr.toString())
    Log.d("foodStorage", lunchArr.toString())
    Log.d("foodStorage", dinnerArr.toString())
    Log.d("foodStorage", snacksArr.toString())

    return FoodItems(
        breakfast = breakfastArr,
        lunch = lunchArr,
        dinner = dinnerArr,
        snacks = snacksArr
    )
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

suspend fun findFoodsInDates(uid: String, foodItem: FoodItem, date: LocalDate): FoodItem? {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

    val food = users
        .document(uid)
        .collection(DATESCOLLECTION)
        .document(date.format(formatter))
        .collection(foodItem.meal.lowercase())
        .document(foodItem.name)
        .get()
        .await()

    return food.toObject(FoodItem::class.java)
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

    return foods.map { food -> food.toObject(FoodItem::class.java) }
}

suspend fun deleteFoodFromMeals(
    uid: String,
    foodName: String,
) {
    users
        .document(uid)
        .collection(FOODCOLLECTION)
        .document(foodName)
        .delete()
        .await()
}

suspend fun deleteFoodFromDates(
    uid: String,
    foodName: String,
    meal: String,
    date: LocalDate
) {
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    val formattedDateStr = date.format(formatter)

    users
        .document(uid)
        .collection(DATESCOLLECTION)
        .document(formattedDateStr)
        .collection(meal)
        .document(foodName)
        .delete()
        .await()
}