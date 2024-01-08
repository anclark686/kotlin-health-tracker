package com.reyaly.reyalyhealthtracker.helpers

import android.util.Log
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.round
import kotlin.math.roundToInt

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

fun convertHeightToCm(weight: String): String {
    val newWeightStr = weight.replace('"', ' ').replace('\'', ' ')
    val (feet, inches) = newWeightStr.split(" ")
    val totalHeightInInches = (feet.toInt() * 12) + inches.toInt()
    val heightInCm = totalHeightInInches * 2.54
    return heightInCm.toString()
}