package com.reyaly.reyalyhealthtracker.screens

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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.reyaly.reyalyhealthtracker.screens.emailandpw.EmailAndPasswordScreen
import com.reyaly.reyalyhealthtracker.screens.signin.SignInScreen
import com.reyaly.reyalyhealthtracker.screens.signup.SignUpScreen
import kotlinx.coroutines.delay

private val TAG = "Main screen"

enum class MainScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    SignIn(title = R.string.nav_sign_in),
    SignUp(title = R.string.nav_sign_up),
    EmailAndPW(title = R.string.nav_email_and_pw),
    Dashboard(title = R.string.nav_dashboard),
    Settings(title = R.string.nav_settings),
    Food(title = R.string.nav_food),
    Water(title = R.string.nav_water),
    Exercise(title = R.string.nav_exercise),
    Med(title = R.string.nav_med),
    Weight(title = R.string.nav_weight)
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
            containerColor = MaterialTheme.colorScheme.primaryContainer
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
    navController: NavHostController = rememberNavController(),
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MainScreen.valueOf(
        backStackEntry?.destination?.route ?: MainScreen.Home.name
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    fun loginToDashboard() {
        navController.popBackStack(MainScreen.Home.name, inclusive = false, saveState = false)
        navController.navigate(MainScreen.Dashboard.name)
    }

    fun logoutWithRedirect() {
        navController.popBackStack(MainScreen.Home.name, inclusive = false, saveState = false)
        navController.navigate(MainScreen.Home.name)
    }

    Scaffold(
        topBar = {
            MainAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null && currentDestination?.route.toString() != "Home",
                navigateUp = { navController.navigateUp() },
                navToSettings = { navController.navigate(MainScreen.Settings.name) },
                whatPage = currentDestination?.route.toString()
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MainScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MainScreen.Home.name) {
                // TODO

                var exit by remember { mutableStateOf(false) }
                val context = LocalContext.current

                HomeScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.name) },
                    onLoginClick = { navController.navigate(MainScreen.SignIn.name) }
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
            composable(route = MainScreen.SignIn.name) {
                // TODO
                SignInScreen(
                    onEmailAndPwClick = { navController.navigate(MainScreen.EmailAndPW.name) },
                    onSignUpRedirect = { navController.navigate(MainScreen.SignUp.name) }
                )
            }
            composable(route = MainScreen.EmailAndPW.name) {
                // TODO
                EmailAndPasswordScreen(
                    onSuccess = { loginToDashboard() }
                )
            }
            composable(route = MainScreen.SignUp.name) {
                // TODO
                SignUpScreen(
                    onSignInRedirect = { navController.navigate(MainScreen.SignIn.name) },
                    onSuccess = { loginToDashboard() }
                )
            }
            composable(route = MainScreen.Dashboard.name) {
                // TODO
                DashboardScreen(
                    onSettingsClick = { navController.navigate(MainScreen.Settings.name) },
                    onExerciseClick = { navController.navigate(MainScreen.Exercise.name) },
                    onFoodClick = { navController.navigate(MainScreen.Food.name) },
                    onMedClick = { navController.navigate(MainScreen.Med.name) },
                    onWaterClick = { navController.navigate(MainScreen.Water.name) },
                    onWeightClick = { navController.navigate(MainScreen.Weight.name) }
                )
            }
            composable(route = MainScreen.Settings.name) {
                // TODO
                SettingsScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.name) },
                    onLogoutClick = { logoutWithRedirect() }
                )
            }
            composable(route = MainScreen.Food.name) {
                // TODO
                FoodScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.name) }
                )
            }
            composable(route = MainScreen.Water.name) {
                // TODO
                WaterScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.name) }
                )
            }
            composable(route = MainScreen.Exercise.name) {
                // TODO
                ExerciseScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.name) }
                )
            }
            composable(route = MainScreen.Weight.name) {
                // TODO
                WeightScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.name) }
                )
            }
            composable(route = MainScreen.Med.name) {
                // TODO
                MedScreen(
                    onDashboardClick = { navController.navigate(MainScreen.Dashboard.name) }
                )
            }
        }
    }
}



