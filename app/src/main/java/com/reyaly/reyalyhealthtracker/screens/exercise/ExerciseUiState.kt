package com.reyaly.reyalyhealthtracker.screens.exercise

import com.reyaly.reyalyhealthtracker.model.Exercise

data class ExerciseUiState(
    val exerciseList: MutableList<Exercise> = mutableListOf(),
    val cardioList: MutableList<Exercise> = mutableListOf(),
    val strengthList: MutableList<Exercise> = mutableListOf(),
    val cardioTimes: ExerciseTimeData = ExerciseTimeData(),
    val strengthTimes: ExerciseTimeData = ExerciseTimeData(),
    val exercisesAreLoading: Boolean = false,

    val nameError: String? = null,
    val calsBurnedError: String? = null,
    val lengthTimeError: String? = null,
    val timeOfDayError: String? = null,
    val intensityError: String? = null,
    val areaError: String? = null,
    val workoutTypeError: String? = null
)

data class ExerciseTimeData(
    var morning: MutableList<Exercise> = mutableListOf(),
    var afternoon: MutableList<Exercise> = mutableListOf(),
    var evening: MutableList<Exercise> = mutableListOf(),
    var night: MutableList<Exercise> = mutableListOf(),
    var length: Int = 0,
    var timeSpent: String = "",
    var calsBurned: Int = 0,
)
