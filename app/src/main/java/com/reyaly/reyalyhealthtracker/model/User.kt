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
    var weightGoals: String? = "",
    var activityLevel: String? = "",
    var timestamp: Timestamp? = null,
    var timestampStr: String? = null,
    var meds: List<Medication>? = null,
    var joined: Timestamp? = null,
    var joinedStr: String? = null
)
