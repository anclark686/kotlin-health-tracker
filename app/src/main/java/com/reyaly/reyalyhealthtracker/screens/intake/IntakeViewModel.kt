package com.reyaly.reyalyhealthtracker.screens.intake

import android.util.Log
import androidx.lifecycle.ViewModel
import com.reyaly.reyalyhealthtracker.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.reyaly.reyalyhealthtracker.helpers.convertHeightToCm
import com.reyaly.reyalyhealthtracker.helpers.convertWeightToKg
import com.reyaly.reyalyhealthtracker.storage.user.addUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

private const val TAG = "Intake"

class IntakeViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(IntakeUiState())
    val uiState = _uiState.asStateFlow()

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val firstName
        get() = uiState.value.firstName
    private val lastName
        get() = uiState.value.lastName

    private val phoneNum
        get() = uiState.value.phoneNum

    private val birthday
        get() = uiState.value.birthday

    private val height
        get() = uiState.value.height

    private val sex
        get() = uiState.value.sex

    private val gender
        get() = uiState.value.gender

    private val currWeight
        get() = uiState.value.currWeight

    private val goalWeight
        get() = uiState.value.goalWeight

    private val weightGoals
        get() = uiState.value.weightGoals

    private val activityLevel
        get() = uiState.value.activityLevel

    fun onFirstNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(firstName = newValue)
        _uiState.value = _uiState.value.copy(firstNameError = null)
    }

    fun onLastNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(lastName = newValue)
        _uiState.value = _uiState.value.copy(lastNameError = null)
    }

    fun onPhoneNumChange(newValue: String) {
        _uiState.value = _uiState.value.copy(phoneNumError = null)
        _uiState.value = _uiState.value.copy(phoneNum = newValue)
    }

    fun onBirthdayChange(newValue: String) {
        _uiState.value = _uiState.value.copy(birthday = newValue)
        _uiState.value = _uiState.value.copy(birthdayError = null)
    }

    fun onHeightChange(newValue: String) {
        _uiState.value = _uiState.value.copy(height = newValue)
        _uiState.value = _uiState.value.copy(heightError = null)
    }

    fun onSexChange(newValue: String) {
        _uiState.value = _uiState.value.copy(sex = newValue)
        _uiState.value = _uiState.value.copy(sexError = null)
    }

    fun onGenderChange(newValue: String) {
        _uiState.value = _uiState.value.copy(gender = newValue)
        _uiState.value = _uiState.value.copy(genderError = null)
    }

    fun onCurrWeightChange(newValue: String) {
        _uiState.value = _uiState.value.copy(currWeight = newValue)
        _uiState.value = _uiState.value.copy(currWeightError = null)
    }

    fun onGoalWeightChange(newValue: String) {
        _uiState.value = _uiState.value.copy(goalWeight = newValue)
        _uiState.value = _uiState.value.copy(goalWeightError = null)
    }

    fun onWeightGoalsChange(newValue: String) {
        _uiState.value = _uiState.value.copy(weightGoals = newValue)
        _uiState.value = _uiState.value.copy(weightGoalsError = null)
    }

    fun onActivityLevelChange(newValue: String) {
        _uiState.value = _uiState.value.copy(activityLevel = newValue)
        _uiState.value = _uiState.value.copy(activityLevelError = null)
    }

    private val blankMessage = "Field cannot be blank"
    private val chooseOption = "Please select an option"

    private fun validateForm(): Boolean {
        Log.d(TAG, "firstName = $firstName")
        Log.d(TAG, "lastName = $lastName")
        Log.d(TAG, "phoneNum = $phoneNum")
        Log.d(TAG, "birthday = $birthday")
        Log.d(TAG, "height = $height")
        Log.d(TAG, "sex = $sex")
        Log.d(TAG, "gender = $gender")
        Log.d(TAG, "currWeight = $currWeight")
        Log.d(TAG, "goalWeight = $goalWeight")
        Log.d(TAG, "weightGoals = $weightGoals")
        Log.d(TAG, "activityLevel = $activityLevel")

        Log.d(TAG, "currWeightInKG = ${convertWeightToKg(currWeight)}")
        Log.d(TAG, "goalWeightInKG = ${convertWeightToKg(goalWeight)}")
        Log.d(TAG, "heightInCM = ${convertHeightToCm(height)}")

        var invalidCount = 0
        if (firstName.isBlank()) {
            _uiState.value = uiState.value.copy(firstNameError = blankMessage)
            invalidCount++
        }
        if (lastName.isBlank()) {
            _uiState.value = uiState.value.copy(lastNameError = blankMessage)
            invalidCount++
        }
        if (phoneNum.isBlank()) {
            _uiState.value = uiState.value.copy(phoneNumError = blankMessage)
            invalidCount++
        }
        if (birthday.isBlank()) {
            _uiState.value = uiState.value.copy(birthdayError = blankMessage)
            invalidCount++
        }
        if (height.isBlank()) {
            _uiState.value = uiState.value.copy(heightError = chooseOption)
            invalidCount++
        }
        if (sex.isBlank()) {
            _uiState.value = uiState.value.copy(sexError = chooseOption)
            invalidCount++
        }
        if (gender.isBlank()) {
            _uiState.value = uiState.value.copy(genderError = chooseOption)
            invalidCount++
        }
        if (currWeight.isBlank()) {
            _uiState.value = uiState.value.copy(currWeightError = blankMessage)
            invalidCount++
        }
        if (goalWeight.isBlank()) {
            _uiState.value = uiState.value.copy(goalWeightError = blankMessage)
            invalidCount++
        }
        if (weightGoals.isBlank()) {
            _uiState.value = uiState.value.copy(weightGoalsError = chooseOption)
            invalidCount++
        }
        if (activityLevel.isBlank()) {
            _uiState.value = uiState.value.copy(activityLevelError = chooseOption)
            invalidCount++
        }

        return invalidCount == 0
    }

    suspend fun submitIntake(callback: () -> Unit) {
        val firebaseUser = auth.currentUser!!
        if (validateForm()) {
            val user = User(
                uid = firebaseUser.uid,
                email = firebaseUser.email,
                firstName = firstName,
                lastName = lastName,
                phoneNum = phoneNum,
                birthday = birthday,
                height = height,
                heightInCm = convertHeightToCm(height),
                sex = sex,
                gender = gender,
                currWeight = currWeight,
                currWeightInKg = convertWeightToKg(currWeight),
                goalWeight = goalWeight,
                goalWeightInKg = convertWeightToKg(goalWeight),
                weightGoals = weightGoals,
                activityLevel = activityLevel,
            )
            Log.d(TAG, "Do something with the form input...")
            try {
                addUser(user).also { callback() }

                Log.d(TAG, "User Added")
            } catch (e: Exception) {
                Log.d(TAG, "an error occurred: $e")
            }
        } else {
            return
        }
    }
}