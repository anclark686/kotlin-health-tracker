package com.reyaly.reyalyhealthtracker.screens.intake

data class IntakeUiState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNum: String = "",
    val birthday: String = "",
    val height: String = "",
    val sex: String = "",
    val gender: String = "",
    val currWeight: String = "",
    val goalWeight: String = "",
    val weightGoals: String = "",
    val activityLevel: String = "",

    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val phoneNumError: String? = null,
    val birthdayError: String? = null,
    val heightError: String? = null,
    val sexError: String? = null,
    val genderError: String? = null,
    val currWeightError: String? = null,
    val goalWeightError: String? = null,
    val weightGoalsError: String? = null,
    val activityLevelError: String? = null,
)


