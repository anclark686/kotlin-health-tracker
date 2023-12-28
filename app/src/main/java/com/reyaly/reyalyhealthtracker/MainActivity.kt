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
                    )
                }
            }
        }
    }

}