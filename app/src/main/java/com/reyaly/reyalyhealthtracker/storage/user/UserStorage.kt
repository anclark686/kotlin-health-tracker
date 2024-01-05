package com.reyaly.reyalyhealthtracker.storage.user

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reyaly.reyalyhealthtracker.model.Medication
import com.reyaly.reyalyhealthtracker.model.User
import kotlinx.coroutines.tasks.await

private const val TAG = "userStorage"
val users = Firebase.firestore.collection("users")
suspend fun addUser(user:User) {
    users
        .document(user.uid)
        .set(user)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
    users.document(user.uid).update("joined", FieldValue.serverTimestamp()).await()
}
suspend fun findUser(uid: String): User? {
    val userData = users.document(uid).get().await()
    val meds = users.document(uid).collection("meds").get().await()
    if (userData != null) {
        val user = userData.toObject(User::class.java)
        user?.meds = meds.map { med ->  med.toObject(Medication::class.java) }
        return user
    }
    return null
}

suspend fun updateUserLoginTime(uid: String) {
    users.document(uid).update("timestamp", FieldValue.serverTimestamp()).await()
}

suspend fun deleteUserData(uid: String) {
    users.document(uid).delete().await()
}