package com.reyaly.reyalyhealthtracker.screens.intake

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "Intake"

class IntakeViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(IntakeUiState())
    val uiState = _uiState.asStateFlow()

    private val _medState = MutableStateFlow(Medication())
    val medState = _medState.asStateFlow()

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

    private val medList
        get() = uiState.value.medList

    private val name
        get() = medState.value.name

    private val dose
        get() = medState.value.dose

    private val time
        get() = medState.value.time


    fun onFirstNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(firstName = newValue)
        _uiState.value = _uiState.value.copy(firstNameError = null)
    }

    fun onLastNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(lastName = newValue)
        _uiState.value = _uiState.value.copy(lastNameError = null)
    }

    fun onPhoneNumChange(newValue: String) {
        _uiState.value = _uiState.value.copy(phoneNum = newValue)
        _uiState.value = _uiState.value.copy(phoneNumError = null)
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

    fun onNameChange(newValue: String) {
        _medState.value = medState.value.copy(name = newValue)
        _uiState.value = uiState.value.copy(nameError = null)
    }

    fun onDoseChange(newValue: String) {
        _medState.value = medState.value.copy(dose = newValue)
        _uiState.value = uiState.value.copy(doseError = null)
    }

    fun onTimeChange(newValue: String) {
        _medState.value = medState.value.copy(time = newValue)
        _uiState.value = uiState.value.copy(timeError = null)
    }

    private val blankMessage = "Field cannot be blank"
    private val chooseOption = "Please select an option"

    private fun validateMed(): Boolean {
        Log.d(TAG, "name = $name")
        Log.d(TAG, "dose = $dose")
        Log.d(TAG, "time = $time")

        var invalidCount = 0
        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(nameError = blankMessage)
            invalidCount++
        }
        if (dose.isBlank()) {
            _uiState.value = _uiState.value.copy(doseError = blankMessage)
            invalidCount++
        }
        if (time.isBlank()) {
            _uiState.value = _uiState.value.copy(timeError = blankMessage)
            invalidCount++
        }

        return invalidCount == 0
    }

    fun onAddNewMed() {
        if (validateMed()) {
            medList.add(Medication(name, dose, time))
            Log.d(TAG, _uiState.value.medList.toString())
            _medState.value = medState.value.copy(name = "")
            _medState.value = medState.value.copy(dose = "")
            _medState.value = medState.value.copy(time = "")
        } else {
            return
        }
    }

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

    fun submitIntake() {
        if (validateForm()) {
            Log.d(TAG, "Do something with the form input...")
        } else {
            return
        }
    }
}