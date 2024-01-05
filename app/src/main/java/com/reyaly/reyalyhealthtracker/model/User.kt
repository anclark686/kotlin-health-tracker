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
    var sex: String? = "",
    var gender: String? = "",
    var currWeight: String? = "",
    var goalWeight: String? = "",
    var weightGoals: String? = "",
    var activityLevel: String? = "",
    var timestamp: Timestamp? = null,
    var meds: List<Medication>? = null,
    var joined: Timestamp? = null
)
