package com.reyaly.reyalyhealthtracker.screens.snack

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.storage.food.addFoodToDailyMeals
import com.reyaly.reyalyhealthtracker.storage.food.addFoodToUsersFoods
import com.reyaly.reyalyhealthtracker.storage.food.findMealsInDates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

private const val TAG = "foodForm"

class SnackViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(SnackUiState())
    val uiState = _uiState.asStateFlow()

    private val _foodState = MutableStateFlow(FoodItem())
    val foodState = _foodState.asStateFlow()

    private val foodList
        get() = uiState.value.foodList

    private val name
        get() = foodState.value.name

    private val calories
        get() = foodState.value.calories

    private val protein
        get() = foodState.value.protein

    private val fat
        get() = foodState.value.fat

    private val carbs
        get() = foodState.value.carbs

    private val quantity
        get() = foodState.value.quantity

    private val apiId
        get() = foodState.value.apiId

    fun onNameChange(newValue: String) {
        _foodState.value = _foodState.value.copy(name = newValue)
        _uiState.value = _uiState.value.copy(nameError = null)
    }

    fun onCaloriesChange(newValue: String) {
        _foodState.value = _foodState.value.copy(calories = newValue)
        _uiState.value = _uiState.value.copy(caloriesError = null)
    }

    fun onProteinChange(newValue: String) {
        _foodState.value = _foodState.value.copy(protein = newValue)
        _uiState.value = _uiState.value.copy(proteinError = null)
    }

    fun onFatChange(newValue: String) {
        _foodState.value = _foodState.value.copy(fat = newValue)
        _uiState.value = _uiState.value.copy(fatError = null)
    }

    fun onCarbsChange(newValue: String) {
        _foodState.value = _foodState.value.copy(carbs = newValue)
        _uiState.value = _uiState.value.copy(carbsError = null)
    }

    fun onQuantityChange(newValue: String) {
        _foodState.value = _foodState.value.copy(quantity = newValue)
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
        if (quantity == "0") {
            _uiState.value = uiState.value.copy(quantityError = chooseOption)
            invalidCount++
        }
        return invalidCount == 0
    }

    suspend fun addFoodManual(date: LocalDate, openDialog: MutableState<Boolean>) {
        val firebaseUser = auth.currentUser!!

        if (validateForm()) {
            val newFood = FoodItem(
                documentId = name.lowercase(),
                meal = "snack",
                name = name.lowercase(),
                calories = calories,
                protein = protein,
                fat = fat,
                carbs = carbs,
                quantity = quantity,
                apiId = apiId
            )
            Log.d(TAG, newFood.toString())
            Log.d(TAG, date.toString())
            try {
                // need to add check to see if item is already in foods
                addFoodToUsersFoods(firebaseUser.uid, newFood)
                Log.d(TAG, "Successfully added to foods")
                try {
                    addFoodToDailyMeals(firebaseUser.uid, newFood, date)
                    Log.d(TAG, "Successfully added to date")
                    foodList.add(newFood)
                    Log.d(TAG, "newfood")
                    Log.d(TAG, newFood.toString())
                    Log.d(TAG, "foodList")
                    Log.d(TAG, foodList.toString())
                    Log.d(TAG, "statefoodlist")
                    Log.d(TAG, _uiState.value.foodList.toString())
                    _uiState.update { currentState ->
                        currentState.copy(
                            foodList = foodList
                        )
                    }
                    _foodState.value = foodState.value.copy(name = "")
                    _foodState.value = foodState.value.copy(calories = "")
                    _foodState.value = foodState.value.copy(protein = "")
                    _foodState.value = foodState.value.copy(fat = "")
                    _foodState.value = foodState.value.copy(carbs = "")
                    _foodState.value = foodState.value.copy(quantity = "")
                    _foodState.value = foodState.value.copy(apiId = "")
                    openDialog.value = false
                } catch (e: Exception) {
                    Log.d(TAG, "an error occurred: $e")
                }
            } catch (e: Exception) {
                Log.d(TAG, "an error occurred: $e")
            }
        }
        return
    }

    suspend fun getUsersMeals(date: LocalDate) {
        val firebaseUser = auth.currentUser!!
        _uiState.value = _uiState.value.copy(foodsAreLoading = true)

        try {
            val foods = findMealsInDates(firebaseUser.uid, "snack", date).toMutableList().also {
                _uiState.value = _uiState.value.copy(foodsAreLoading = false)
            }
            Log.d(TAG, foods.toString())
            _uiState.value = _uiState.value.copy(foodList = foods)
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
    }
//        updateMeal(firebaseUser.uid, food!!.documentId, "snack", LocalDate.now())
//    }

    suspend fun getFoods() {

    }
}