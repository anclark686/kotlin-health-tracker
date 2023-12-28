package com.reyaly.reyalyhealthtracker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.reyaly.reyalyhealthtracker.ui.theme.ReyalyHealthTrackerTheme
import com.reyaly.reyalyhealthtracker.screens.MainApp
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.reyaly.reyalyhealthtracker.viewmodels.user.UserViewModel

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var auth: FirebaseAuth


//    private val signInLauncher = registerForActivityResult(
//        FirebaseAuthUIActivityResultContract(),
//    ) { res ->
//        this.onSignInResult(res)
//    }
//
//    val providers = arrayListOf(
//        AuthUI.IdpConfig.EmailBuilder().build(),
//        AuthUI.IdpConfig.PhoneBuilder().build(),
//        AuthUI.IdpConfig.GoogleBuilder().build(),
//    )
//
//    // Create and launch sign-in intent
//    val signInIntent = AuthUI.getInstance()
//        .createSignInIntentBuilder()
//        .setAvailableProviders(providers)
//        .build()
//
//
//
//    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
//        val response = result.idpResponse
//        if (result.resultCode == RESULT_OK) {
//            // Successfully signed in
//            val user = FirebaseAuth.getInstance().currentUser
//            Log.d(TAG, "User logged in")
//            // ...
//        } else {
//            // Sign in failed. If response is null the user canceled the
//            // sign-in flow using the back button. Otherwise check
//            // response.getError().getErrorCode() and handle the error.
//            // ...
//            Log.d(TAG, "Unable to login user")
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        userViewModel.setContext(this)

        setContent {
            ReyalyHealthTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp(
                        userViewModel = userViewModel,
//                        signInLauncher = { signInLauncher.launch(signInIntent) }
                    )
                }
            }
        }
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            Log.d(TAG, "Logged in!")
//        }
//    }

//    fun createAccount(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
////                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
////                    updateUI(null)
//                }
//            }
//    }

}