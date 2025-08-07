package com.reyaly.reyalyhealthtracker.screens.exercise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.model.Exercise
import com.reyaly.reyalyhealthtracker.storage.exercise.addExerciseToDates
import com.reyaly.reyalyhealthtracker.storage.exercise.addExerciseToExercises
import com.reyaly.reyalyhealthtracker.storage.exercise.getExercisesFromDates
import com.reyaly.reyalyhealthtracker.storage.exercise.getExercisesFromExercises
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "exercise-view-model"
private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

class ExerciseViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(ExerciseUiState())
    val uiState = _uiState.asStateFlow()

    private val _exerciseState = MutableStateFlow(Exercise())
    val exerciseState = _exerciseState.asStateFlow()

    private val exerciseList
        get() = uiState.value.exerciseList

    private val name
        get() = exerciseState.value.name

    private val calsBurned
        get() = exerciseState.value.calsBurned

    private val lengthTime
        get() = exerciseState.value.lengthTime

    private val timeOfDay
        get() = exerciseState.value.timeOfDay

    private val intensity
        get() = exerciseState.value.intensity

    private val area
        get() = exerciseState.value.area

    fun onNameChange(newValue: String) {
        _exerciseState.value = exerciseState.value.copy(name = newValue)
        _uiState.value = uiState.value.copy(nameError = null)
    }

    fun onCalsBurnedChange(newValue: String) {
        _exerciseState.value = exerciseState.value.copy(calsBurned = newValue)
        _uiState.value = uiState.value.copy(calsBurnedError = null)
    }

    fun onLengthTimeChange(newValue: String) {
        _exerciseState.value = exerciseState.value.copy(lengthTime = newValue)
        _uiState.value = uiState.value.copy(lengthTimeError = null)
    }

    fun onTimeOfDayChange(newValue: String) {
        _exerciseState.value = exerciseState.value.copy(timeOfDay = newValue.lowercase())
        _uiState.value = uiState.value.copy(timeOfDayError = null)
    }

    fun onIntensityChange(newValue: String) {
        _exerciseState.value = exerciseState.value.copy(intensity = newValue)
        _uiState.value = uiState.value.copy(intensityError = null)
    }

    fun onAreaChange(newValue: String) {
        _exerciseState.value = exerciseState.value.copy(area = newValue)
        _uiState.value = uiState.value.copy(areaError = null)
    }

    private val blankMessage = "Field cannot be blank"
    private val selectAnOption = "Please select at least one option"
    private val notANumber = "Value must be a number"

    private fun validateForm(workoutType: String): Boolean {
        var invalidCount = 0

        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(nameError = blankMessage)
            invalidCount++
        }

        if (calsBurned.isBlank()) {
            _uiState.value = _uiState.value.copy(calsBurnedError = blankMessage)
            invalidCount++
        }

        try {
            calsBurned.toFloat()
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(calsBurnedError = notANumber)
            invalidCount++
        }

        if (lengthTime.isBlank()) {
            _uiState.value = _uiState.value.copy(lengthTimeError = blankMessage)
            invalidCount++
        }

        try {
            lengthTime.toFloat()
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(lengthTimeError = notANumber)
            invalidCount++
        }

        if (timeOfDay.isBlank()) {
            _uiState.value = _uiState.value.copy(timeOfDayError = selectAnOption)
            invalidCount++
        }

        if (intensity.isBlank()) {
            _uiState.value = _uiState.value.copy(intensityError = selectAnOption)
            invalidCount++
        }

        if (area.isBlank() && workoutType == "strength") {
            _uiState.value = _uiState.value.copy(areaError = blankMessage)
            invalidCount++
        }

        return invalidCount == 0
    }

    fun clearEverything() {
        _exerciseState.value = exerciseState.value.copy(
            name = "",
            calsBurned = "",
            lengthTime = "",
            timeOfDay = "",
            intensity = "",
            area = "",
        )
    }

    fun populateFieldsWithInitialValues(exercise: Exercise, workoutType: String) {
        Log.d(TAG, "hello")
    }

    suspend fun onAddNewExercise(workoutType: String, date: LocalDate): Boolean {
        val firebaseUser = auth.currentUser!!

        Log.d(TAG, name)
        Log.d(TAG, calsBurned)
        Log.d(TAG, lengthTime)
        Log.d(TAG, timeOfDay)
        Log.d(TAG, intensity)
        Log.d(TAG, area)
        Log.d(TAG, workoutType)

        if (validateForm(workoutType)) {
            Log.d(TAG, "wassup")

            val exercise = Exercise(
                name = name.lowercase(),
                calsBurned = calsBurned,
                lengthTime = lengthTime,
                timeOfDay = timeOfDay.lowercase(),
                intensity = intensity,
                area = area.lowercase(),
                workoutType = workoutType.lowercase(),
            )

            try {
                addExerciseToExercises(firebaseUser.uid, exercise)

                addExerciseToDates(firebaseUser.uid, exercise, date.format(formatter))

                exerciseList.add(exercise)

                clearEverything()
            } catch (e: Exception) {
                Log.d(TAG, "an error occurred: $e")
            }

            return true
        }
        return false
    }

    suspend fun addExistingExercise(exercise: Exercise, date: LocalDate): Boolean {
        val firebaseUser = auth.currentUser!!

        try {
            addExerciseToDates(firebaseUser.uid, exercise, date.format(formatter))

            return true
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }

        return false
    }

    private fun sortAllByType(exercises: List<Exercise>) {
        val cardio: MutableList<Exercise> = mutableListOf()
        val strength: MutableList<Exercise> = mutableListOf()

        exercises.forEach { exercise ->
            when (exercise.workoutType) {
                "cardio" -> cardio.add(exercise)
                "strength" -> strength.add(exercise)
            }
        }

        _uiState.value = _uiState.value.copy(
            cardioList = cardio,
            strengthList = strength
        )

//        Log.d(TAG, "uiState.value.cardioList.toString()")
//        Log.d(TAG, uiState.value.cardioList.toString())
//
//        Log.d(TAG, "uiState.value.strengthList.toString()")
//        Log.d(TAG, uiState.value.strengthList.toString())
    }

    private fun getTimeSpent(exercises: List<Exercise>): Int {
        var time = 0

        exercises.forEach { exercise ->
            time += exercise.lengthTime.toInt()
        }

        return time
    }

    private fun getCalsBurned(exercises: List<Exercise>): Int {
        var calsBurned = 0

        exercises.forEach { exercise ->
            calsBurned += exercise.calsBurned.toInt()
        }

        return calsBurned
    }

    private fun sortIntoTimes(exercises: List<Exercise>, workoutType: String) {
        val exerciseTimeData = ExerciseTimeData(
            morning = mutableListOf(),
            afternoon = mutableListOf(),
            evening = mutableListOf(),
            night = mutableListOf(),
            length = exercises.count(),
            timeSpent = "${getTimeSpent(exercises)} mins",
            calsBurned = getCalsBurned(exercises),
        )

//        val exerciseNameAndTimes: HashMap<String, List<String>> = hashMapOf()

        exercises.forEach { exercise ->
            if (exercise.timeOfDay == "morning") {
                exerciseTimeData.morning.add(exercise)
            }
            if (exercise.timeOfDay == "afternoon") {
                exerciseTimeData.afternoon.add(exercise)
            }
            if (exercise.timeOfDay == "evening") {
                exerciseTimeData.evening.add(exercise)
            }
            if (exercise.timeOfDay == "night") {
                exerciseTimeData.night.add(exercise)
            }

//            exerciseNameAndTimes[exercise.name] = exercise.times
        }

        if (workoutType == "cardio") {
            _uiState.value = _uiState.value.copy(cardioTimes = exerciseTimeData)
        } else {
            _uiState.value = _uiState.value.copy(strengthTimes = exerciseTimeData)
        }

//        Log.d(TAG, "uiState.value.cardioTimes.toString()")
//        Log.d(TAG, uiState.value.cardioTimes.toString())
//
//        Log.d(TAG, "uiState.value.strengthTimes.toString()")
//        Log.d(TAG, uiState.value.strengthTimes.toString())
    }

    suspend fun getExercises(workoutType: String, date: LocalDate) {
        val firebaseUser = auth.currentUser!!

        try {
            val baseExercises = getExercisesFromExercises(firebaseUser.uid)
            Log.d(TAG, baseExercises.toString())
            Log.d(TAG, "THE END")

            sortAllByType(baseExercises)

            exerciseList.addAll(baseExercises)

            val dailyExercises = getExercisesFromDates(firebaseUser.uid, workoutType, date.format(
                formatter))

            sortIntoTimes(dailyExercises, workoutType)
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
    }
}