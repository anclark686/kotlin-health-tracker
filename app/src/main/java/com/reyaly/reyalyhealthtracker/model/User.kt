package com.reyaly.reyalyhealthtracker.model

import com.google.firebase.Timestamp

data class User(
    var uid: String = "",
    var email: String? = "",
    var firstName: String? = "",
    var lastName: String? = "",
    var phoneNum: String? = "",
    var birthday: String? = "",
    var height: String? = "",
    var heightInCm: String? = "",
    var sex: String? = "",
    var gender: String? = "",
    var currWeight: String? = "",
    var currWeightInKg: String? = "",
    var goalWeight: String? = "",
    var goalWeightInKg: String? = "",
    var initialWeight: String? = "",
    var initialWeightInKg: String? = "",
    var previousWeight: String? = "",
    var previousWeightInKg: String? = "",
    var lowestWeight: String? = "",
    var lowestWeightInKg: String? = "",
    var highestWeight: String? = "",
    var highestWeightInKg: String? = "",
    var weightGoals: String? = "",
    var calorieGoal: String? = "",
    var fatGoal: String? = "",
    var proteinGoal: String? = "",
    var carbsGoal: String? = "",
    var activityLevel: String? = "",
    var timestamp: Timestamp? = null,
    var timestampStr: String? = null,
    var joined: Timestamp? = null,
    var joinedStr: String? = null
)
