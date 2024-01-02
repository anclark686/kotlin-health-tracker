package com.reyaly.reyalyhealthtracker.screens

import android.app.Activity.RESULT_OK
import android.content.Context
import androidx.activity.result.ActivityResult
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.screens.dashboard.DashboardScreen
import com.reyaly.reyalyhealthtracker.screens.exercise.ExerciseScreen
import com.reyaly.reyalyhealthtracker.screens.food.FoodScreen
import com.reyaly.reyalyhealthtracker.screens.home.HomeScreen
import com.reyaly.reyalyhealthtracker.screens.med.MedScreen
import com.reyaly.reyalyhealthtracker.screens.settings.SettingsScreen
import com.reyaly.reyalyhealthtracker.screens.water.WaterScreen
import com.reyaly.reyalyhealthtracker.screens.weight.WeightScreen
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.reyaly.reyalyhealthtracker.screens.changepw.ChangePwScreen
import com.reyaly.reyalyhealthtracker.screens.emailandpw.EmailAndPasswordScreen
import com.reyaly.reyalyhealthtracker.screens.emailandpw.EmailAndPwViewModel
import com.reyaly.reyalyhealthtracker.screens.intake.IntakeScreen
import com.reyaly.reyalyhealthtracker.screens.resetemail.ResetEmailSentScreen
import com.reyaly.reyalyhealthtracker.screens.signin.GoogleAuthUiClient
import com.reyaly.reyalyhealthtracker.screens.signin.SignInScreen
import com.reyaly.reyalyhealthtracker.screens.signin.SignInViewModel
import com.reyaly.reyalyhealthtracker.screens.signup.SignUpScreen
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val TAG = "Main screen"

enum class MainScreen(@StringRes val title: Int, val route: String) {
    Home(title = R.string.app_name, route = "Home"),
    SignIn(title = R.string.nav_sign_in, route = "SignIn"),
    SignInModify(title = R.string.nav_sign_in, route = "SignIn/{modify}"),
    SignUp(title = R.string.nav_sign_up, route = "SignUp"),
    EmailAndPW(title = R.string.nav_email_and_pw, route = "EmailAndPW"),
    EmailAndPWModify(title = R.string.nav_email_and_pw, route = "EmailAndPW/{modify}"),
    ResetEmail(title = R.string.nav_reset_email, route = "ResetEmail"),
    ChangePassword(title = R.string.nav_change_pw, route = "ChangePassword"),
    Intake(title = R.string.nav_intake, route = "Intake"),
    Dashboard(title = R.string.nav_dashboard, route = "Dashboard"),
    Settings(title = R.string.nav_settings, route = "Settings"),
    Food(title = R.string.nav_food, route = "Food"),
    Water(title = R.string.nav_water, route = "Water"),
    Exercise(title = R.string.nav_exercise, route = "Exercise"),
    Med(title = R.string.nav_med, route = "Med"),
    Weight(title = R.string.nav_weight, route = "Weight"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    currentScreen: MainScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navToSettings: () -> Unit,
    whatPage: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = if (isSystemInDarkTheme()) dark_sky_blue else light_sky_blue
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (whatPage != "Settings") {
                IconButton(onClick = { navToSettings() }) {
                    Icon(Icons.Filled.Settings, null)
                }
            }
        }
    )
}

@Composable
fun MainApp(
    lifecycleScope: LifecycleCoroutineScope,
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context,
    navController: NavHostController = rememberNavController()
) {

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    var currentStr: String?
    if (backStackEntry?.destination?.route?.contains("modify") == true) {
        currentStr = backStackEntry?.destination?.route?.replace("/{modify}", "")
    } else {
        currentStr = backStackEntry?.destination?.route
    }
    val currentScreen = MainScreen.valueOf(
        currentStr ?: MainScreen.Home.route
    )

    fun loginToDashboard() {
        navController.popBackStack(MainScreen.Home.route, inclusive = false, saveState = false)
        navController.navigate(MainScreen.Dashboard.route)
    }

    fun toSettingsWithToast() {
        navController.popBackStack(MainScreen.Home.route, inclusive = false, saveState = false)
        navController.navigate(MainScreen.Settings.route)
        Toast.makeText(
            applicationContext,
            "Password successfully changed.",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun logoutWithRedirect() {
        navController.popBackStack(MainScreen.Home.route, inclusive = false, saveState = false)
        navController.navigate(MainScreen.Home.route)
    }

    Scaffold(
        topBar = {
            MainAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null && currentScreen.toString() != "Home",
                navigateUp = { navController.navigateUp() },
                navToSettings = { navController.navigate(MainScreen.Settings.route) },
                whatPage = currentScreen.toString()
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MainScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            Log.d(TAG, currentScreen.toString())
            composable(route = MainScreen.Home.route) {
                Log.d("main", MainScreen.Home.route)
                var exit by remember { mutableStateOf(false) }
                val context = LocalContext.current

                HomeScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.route) },
                    onLoginClick = { navController.navigate(MainScreen.SignIn.route) },
                    onIntakeClick = { navController.navigate(MainScreen.Intake.route) }
                )

                LaunchedEffect(key1 = exit) {
                    if (exit) {
                        delay(2000)
                        exit = false
                    }
                }

                BackHandler(enabled = true) {
                    if (exit) {
                        context.startActivity(Intent(Intent.ACTION_MAIN).apply {
                            addCategory(Intent.CATEGORY_HOME)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    } else {
                        exit = true
                        Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            composable(route = MainScreen.SignIn.route) {
                val signInViewModel = viewModel<SignInViewModel>()
                val signInstate by signInViewModel.state.collectAsStateWithLifecycle()

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )

                                signInViewModel.onSignInResult(signInResult, modify = null, callback = { loginToDashboard() })

                                Log.d(TAG, "Sign in successful")
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = signInstate.isDeleteSuccessful) {
                    if(signInstate.isDeleteSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Account successfully deleted.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                SignInScreen(
                    onEmailAndPwClick = { navController.navigate(MainScreen.EmailAndPW.route) },
                    onEmailAndPwClickDelete = { navController.navigate("EmailAndPW/delete") },
                    onEmailAndPwClickChange = { navController.navigate("EmailAndPW/change") },
                    onSignInClick = {
                        lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    onResetPassword = {  },
                    onDeleteAccount = {  },
                    modify = null
                )
            }

            composable(route = "SignIn/{modify}", arguments = listOf(navArgument("modify") { type = NavType.StringType })) {
                val modify = it.arguments?.getString("modify")
                Log.d("mainScreen", modify.toString())

                val signInViewModel = viewModel<SignInViewModel>()
                val signInstate by signInViewModel.state.collectAsStateWithLifecycle()

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )

                                if (modify == "delete") {
                                    signInViewModel.onSignInResult(signInResult, modify = modify, callback = { navController.navigate(MainScreen.Home.route) })
                                } else {
                                    signInViewModel.onSignInResult(signInResult, modify = modify, callback = { loginToDashboard() })
                                }
                                Log.d(TAG, "Sign in successful")
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = signInstate.isDeleteSuccessful) {
                    if(signInstate.isDeleteSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Account successfully deleted.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                SignInScreen(
                    onEmailAndPwClick = { navController.navigate(MainScreen.EmailAndPW.route) },
                    onEmailAndPwClickDelete = { navController.navigate("EmailAndPW/delete") },
                    onEmailAndPwClickChange = { navController.navigate("EmailAndPW/change") },
                    onSignInClick = {
                        lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    onResetPassword = {  },
                    onDeleteAccount = {  },
                    modify = modify
                )
            }

            composable(route = MainScreen.EmailAndPW.route) {
                EmailAndPasswordScreen(
                    onSuccess = { loginToDashboard() },
                    onSignUpRedirect = { navController.navigate(MainScreen.SignUp.route) },
                    onForgotPw = { navController.navigate(MainScreen.ResetEmail.route) },
                    onResetPassword = { navController.navigate(MainScreen.ChangePassword.route) },
                    onDeleteAccount = { navController.navigate(MainScreen.Home.route) },
                    modify = null
                )
            }

            composable(route = "EmailAndPW/{modify}", arguments = listOf(navArgument("modify") { type = NavType.StringType })) {
                val modify = it.arguments?.getString("modify")
                Log.d("mainScreen", modify.toString())

                EmailAndPasswordScreen(
                    onSuccess = { loginToDashboard() },
                    onSignUpRedirect = { navController.navigate(MainScreen.SignUp.route) },
                    onForgotPw = { navController.navigate(MainScreen.ResetEmail.route) },
                    onResetPassword = { navController.navigate(MainScreen.ChangePassword.route) },
                    onDeleteAccount = { navController.navigate(MainScreen.Home.route) },
                    modify = modify
                )
            }

            composable(route = MainScreen.SignUp.route) {
                SignUpScreen(
                    onSignInRedirect = { navController.navigate(MainScreen.EmailAndPW.route) },
                    onSuccess = { loginToDashboard() }
                )
            }

            composable(route = MainScreen.ResetEmail.route) {
                ResetEmailSentScreen(
                    onLoginClick = { navController.navigate(MainScreen.EmailAndPW.route) }
                )
            }

            composable(route = MainScreen.Dashboard.route) {
                DashboardScreen(
                    onExerciseClick = { navController.navigate(MainScreen.Exercise.route) },
                    onFoodClick = { navController.navigate(MainScreen.Food.route) },
                    onMedClick = { navController.navigate(MainScreen.Med.route) },
                    onWaterClick = { navController.navigate(MainScreen.Water.route) },
                    onWeightClick = { navController.navigate(MainScreen.Weight.route) }
                )
            }

            composable(route = MainScreen.Settings.route) {
                SettingsScreen(
                    onLoginClick = { navController.navigate(MainScreen.SignIn.route) },
                    onLoginChangeClick = { navController.navigate("SignIn/change") },
                    onLoginDeleteClick = { navController.navigate("SignIn/delete") },
                    onLogoutClick = { logoutWithRedirect() }
                )
            }

            composable(route = MainScreen.ChangePassword.route) {
                ChangePwScreen(
                    onSettingsRedirect = { toSettingsWithToast() }
                )
            }

            composable(route = MainScreen.Intake.route) {
                IntakeScreen(

                )
            }

            composable(route = MainScreen.Food.route) {
                FoodScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.route) }
                )
            }

            composable(route = MainScreen.Water.route) {
                WaterScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.route) }
                )
            }

            composable(route = MainScreen.Exercise.route) {
                ExerciseScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.route) }
                )
            }

            composable(route = MainScreen.Weight.route) {
                WeightScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.route) }
                )
            }

            composable(route = MainScreen.Med.route) {
                MedScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.route) }
                )
            }
        }
    }
}



