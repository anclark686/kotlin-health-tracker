package com.reyaly.reyalyhealthtracker.helpers

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.Timestamp
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.model.FoodStat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.round

fun convertTimestampToDateStr(timestamp: Timestamp?): String {
    val date = timestamp!!.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    val usFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return date.format(usFormatter)
}

fun convertWeightToKg(weight: String): String {
    val lbsInt = weight.toInt()
    val kgDbl = round(((lbsInt / 2.205) * 100) / 100)
    return kgDbl.toString()
}

fun convertHeightToCm(height: String): String {
    val newHeightStr = height.replace('"', ' ').replace('\'', ' ')
    val (feet, inches) = newHeightStr.split(" ")
    val totalHeightInInches = (feet.toInt() * 12) + inches.toInt()
    val heightInCm = totalHeightInInches * 2.54
    return heightInCm.toString()
}

fun convertOzToCups(oz: String): String {
    return (round(oz.toDouble() / 8).toInt()).toString()
}

fun convertCupsToOz(cups: String): String {
    return (cups.toInt() * 8).toString()
}

fun changeDate(previousDate: MutableState<LocalDate>, direction: String): MutableState<LocalDate> {
    Log.d("In the helper", direction)
    Log.d("In the helper", (direction == "add").toString())

    var newDate = previousDate

    newDate.value = if (direction == "add") {
        previousDate.value.plusDays(1)
    } else {
        previousDate.value.minusDays(1)
    }
    return newDate
}

fun getFoodStats(foodList: List<FoodItem>): FoodStat {
    var unique = foodList.count()
    var total = 0
    var calories = 0
    var protein = 0
    var fat = 0
    var carbs = 0

    foodList.forEach{ food ->
        var itemQuantity = food.quantity.toInt()

        total += itemQuantity
        calories += food.calories.toInt() * itemQuantity
        protein += food.protein.toInt() * itemQuantity
        fat += food.fat.toInt() * itemQuantity
        carbs += food.carbs.toInt() * itemQuantity
    }

    return FoodStat(unique, total, calories, protein, fat, carbs)
}