package com.reyaly.reyalyhealthtracker.screens.breakfast

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.screens.food.FoodItems
import com.reyaly.reyalyhealthtracker.storage.food.addOrEditFoodsInDates
import com.reyaly.reyalyhealthtracker.storage.food.addOrEditFoodInFoods
import com.reyaly.reyalyhealthtracker.storage.food.deleteFoodFromDates
import com.reyaly.reyalyhealthtracker.storage.food.deleteFoodFromMeals
import com.reyaly.reyalyhealthtracker.storage.food.findAllFoods
import com.reyaly.reyalyhealthtracker.storage.food.findFoodsInDates
import com.reyaly.reyalyhealthtracker.storage.food.findMealsInDates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

private const val TAG = "breakfastForm"

class BreakfastViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(BreakfastUiState())
    val uiState = _uiState.asStateFlow()

    private val _foodState = MutableStateFlow(FoodItem())
    val foodState = _foodState.asStateFlow()

    private val foodList
        get() = uiState.value.foodList

    private val existingFoodObject
        get() = uiState.value.existingFoodObject

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

    fun clearFields() {
        _foodState.value = foodState.value.copy(name = "")
        _foodState.value = foodState.value.copy(calories = "")
        _foodState.value = foodState.value.copy(protein = "")
        _foodState.value = foodState.value.copy(fat = "")
        _foodState.value = foodState.value.copy(carbs = "")
        _foodState.value = foodState.value.copy(quantity = "")
        _foodState.value = foodState.value.copy(apiId = "")
    }

    suspend fun onAddOrEditFoodInFoods(newFood: FoodItem) {
        val firebaseUser = auth.currentUser!!
        addOrEditFoodInFoods(firebaseUser.uid, newFood)
        Log.d(TAG, "Successfully added to foods")
    }

    suspend fun onAddEditFoodInDates(newFood: FoodItem, date: LocalDate, edit: Boolean = false) {
        val firebaseUser = auth.currentUser!!
        newFood.meal = "breakfast"

        var oldFood: FoodItem?
        try {
            oldFood = findFoodsInDates(firebaseUser.uid, newFood, date)
            if (oldFood != null && !edit) {
                newFood.quantity = (oldFood.quantity.toInt() + 1).toString()
            }
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }

        addOrEditFoodsInDates(firebaseUser.uid, newFood, date)
        Log.d(TAG, "Successfully added to date")
        foodList.add(newFood)
        _uiState.update { currentState ->
            currentState.copy(
                foodList = foodList
            )
        }
    }

    suspend fun addOrEditFoodManual(date: LocalDate, food: FoodItem? = null, edit: Boolean = false) {
        Log.d("add", "We be adding")
        Log.d("add", food.toString())

        val newFood: FoodItem

        if (food == null) {
            if (validateForm()) {
                newFood = FoodItem(
                    documentId = name.lowercase(),
                    meal = "breakfast",
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
            } else {
                return
            }
        } else {
            newFood = food
            Log.d("add", newFood.toString())
            try {
                onAddOrEditFoodInFoods(newFood)
                try {
                    onAddEditFoodInDates(newFood, date, edit)
                    clearFields()
                } catch (e: Exception) {
                    Log.d(TAG, "an error occurred: $e")
                }
            } catch (e: Exception) {
                Log.d(TAG, "an error occurred: $e")
            }
        }
    }

    suspend fun getUsersMeals(date: LocalDate) {
        val firebaseUser = auth.currentUser!!
        _uiState.value = _uiState.value.copy(foodsAreLoading = true)

        try {
            val foods = findMealsInDates(firebaseUser.uid, "breakfast", date).toMutableList().also {
                _uiState.value = _uiState.value.copy(foodsAreLoading = false)
            }
            Log.d(TAG, foods.toString())
            _uiState.value = _uiState.value.copy(foodList = foods)
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
    }

    suspend fun getAllFoods(): FoodItems? {
        val firebaseUser = auth.currentUser!!
        _uiState.value = _uiState.value.copy(foodsAreLoading = true)

        try {
            val foods = findAllFoods(firebaseUser.uid).also {
                _uiState.value = _uiState.value.copy(foodsAreLoading = false)
            }
            Log.d(TAG, foods.toString())
            _uiState.value = _uiState.value.copy(existingFoodObject = foods)
            return foods
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }

        return null
    }

    suspend fun editFood(food: FoodItem, date: LocalDate) {
        Log.d("edit", "We be editing")
        Log.d("edit", food.toString())

        val combinedFoodItem = FoodItem(
            documentId = if (name.lowercase() != "") {name.lowercase()} else {food.name.lowercase()},
            meal = "breakfast",
            name = if (name.lowercase() != "") {name.lowercase()} else {food.name.lowercase()},
            calories = if (calories != "") {calories} else {food.calories},
            protein = if (protein != "") {protein} else {food.protein},
            fat = if (fat != "") {fat} else {food.fat},
            carbs = if (carbs != "") {carbs} else {food.carbs},
            quantity = if (quantity != "") {quantity} else {food.quantity},
            apiId = apiId
        )
        Log.d("combined", combinedFoodItem.toString())

        if (name.lowercase() != "" && name.lowercase() != food.name.lowercase()) {
            onDeleteFoodInDates(food, date)
            onDeleteFoodInMeals(food)
        }

        addOrEditFoodManual(date, combinedFoodItem, edit=true)
    }

    suspend fun onDeleteFoodInMeals(food: FoodItem) {
        val firebaseUser = auth.currentUser!!
        Log.d("edit", "We be deleting")
        Log.d("edit", food.toString())
        try {
            deleteFoodFromMeals(firebaseUser.uid, food.name)
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
    }

    suspend fun onDeleteFoodInDates(food: FoodItem, date: LocalDate) {
        val firebaseUser = auth.currentUser!!
        Log.d("edit", "We be deleting")
        Log.d("edit", food.toString())
        try {
            deleteFoodFromDates(firebaseUser.uid, food.name, "breakfast", date)
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
    }
}