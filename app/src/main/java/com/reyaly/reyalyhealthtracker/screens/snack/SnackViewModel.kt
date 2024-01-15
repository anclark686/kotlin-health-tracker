package com.reyaly.reyalyhealthtracker.screens.snack

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.storage.food.addFoodToDB
import com.reyaly.reyalyhealthtracker.storage.food.findFood
import com.reyaly.reyalyhealthtracker.storage.food.updateMeal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

private const val TAG = "foodForm"
class SnackViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(SnackUiState())
    val uiState = _uiState.asStateFlow()

    private val name
        get() = uiState.value.name

    private val calories
        get() = uiState.value.calories

    private val protein
        get() = uiState.value.protein

    private val fat
        get() = uiState.value.fat

    private val carbs
        get() = uiState.value.carbs

    private val quantity
        get() = uiState.value.quantity

    private val apiId
        get() = uiState.value.apiId

    fun onNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(name = newValue)
        _uiState.value = _uiState.value.copy(nameError = null)
    }

    fun onCaloriesChange(newValue: String) {
        _uiState.value = _uiState.value.copy(calories = newValue)
        _uiState.value = _uiState.value.copy(caloriesError = null)
    }

    fun onProteinChange(newValue: String) {
        _uiState.value = _uiState.value.copy(protein = newValue)
        _uiState.value = _uiState.value.copy(proteinError = null)
    }

    fun onFatChange(newValue: String) {
        _uiState.value = _uiState.value.copy(fat = newValue)
        _uiState.value = _uiState.value.copy(fatError = null)
    }

    fun onCarbsChange(newValue: String) {
        _uiState.value = _uiState.value.copy(carbs = newValue)
        _uiState.value = _uiState.value.copy(carbsError = null)
    }

    fun onQuantityChange(newValue: String) {
        _uiState.value = _uiState.value.copy(quantity = newValue.toInt())
        _uiState.value = _uiState.value.copy(quantityError = null)
    }

    private val blankMessage = "Field cannot be blank"
    private val chooseOption = "Please select an option"

    private fun validateForm(): Boolean {
        Log.d(TAG, "name = $name")
        Log.d(TAG, "calories = $calories")
        Log.d(TAG, "protein = $protein")
        Log.d(TAG, "fat = $fat")
        Log.d(TAG, "carbs = $carbs")
        Log.d(TAG, "quantity = ${quantity.toString()}")

        var invalidCount = 0
        if (name.isBlank()) {
            _uiState.value = uiState.value.copy(nameError = blankMessage)
            invalidCount++
        }
        if (calories.isBlank()) {
            _uiState.value = uiState.value.copy(caloriesError = blankMessage)
            invalidCount++
        }
        if (protein.isBlank()) {
            _uiState.value = uiState.value.copy(proteinError = blankMessage)
            invalidCount++
        }
        if (fat.isBlank()) {
            _uiState.value = uiState.value.copy(fatError = blankMessage)
            invalidCount++
        }
        if (carbs.isBlank()) {
            _uiState.value = uiState.value.copy(carbsError = blankMessage)
            invalidCount++
        }
        if (quantity == 0) {
            _uiState.value = uiState.value.copy(quantityError = chooseOption)
            invalidCount++
        }
        return invalidCount == 0
    }

    suspend fun addFoodManual() {
        val firebaseUser = auth.currentUser!!

        if (validateForm()) {
            val newFood = FoodItem(
                meal = "snack",
                name = name,
                calories = calories,
                protein = protein,
                fat = fat,
                carbs = carbs,
                apiId = apiId
            )
            try {
                addFoodToDB(firebaseUser.uid, newFood)
            } catch (e: Exception) {
                Log.d(TAG, "an error occurred: $e")
            }
        }
    }

    suspend fun addFoodToMeal(manual: Boolean) {
        val firebaseUser = auth.currentUser!!
        var food = findFood(firebaseUser.uid, name)
        if (food == null) {
            if (manual) {
                addFoodManual()
                food = findFood(firebaseUser.uid, name)
            }
        }

        updateMeal(firebaseUser.uid, food!!.documentId, "snack", LocalDate.now())
    }

    suspend fun getFoods() {

    }
}