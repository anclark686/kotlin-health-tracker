package com.reyaly.reyalyhealthtracker.storage.exercise

import androidx.compose.ui.text.capitalize
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.model.Exercise
import kotlinx.coroutines.tasks.await
import java.util.Locale

private const val TAG = "exerciseStorage"
private const val EXERCISES = "exercises"
private const val DATES = "dates"
val users = Firebase.firestore.collection("users")

suspend fun addExerciseToExercises(uid: String, exercise: Exercise) {
    val exRef = users
        .document(uid)
        .collection(EXERCISES)
        .document(exercise.name.lowercase())

    exRef.set(exercise).await()
}

suspend fun addExerciseToDates(uid: String, exercise: Exercise, date: String) {
    val exRef = users
        .document(uid)
        .collection(DATES)
        .document(date)
        .collection(exercise.workoutType)
        .document(exercise.name.lowercase())

    exRef.set(exercise).await()
}

suspend fun getExercisesFromExercises(uid: String): List<Exercise> {
    val exRef = users.document(uid).collection(EXERCISES)

    val exercises = exRef.get().await()

    return exercises.map { exercise -> exercise.toObject(Exercise::class.java) }
}

suspend fun getExercisesFromDates(uid: String, workoutType: String, date: String): List<Exercise> {
    val exRef = users.document(uid).collection(DATES).document(date).collection(workoutType)

    val exercises = exRef.get().await()

    return exercises.map { exercise -> exercise.toObject(Exercise::class.java) }
}