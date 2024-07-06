package com.reyaly.reyalyhealthtracker.screens.lunch

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.common.components.DateSelector
import com.reyaly.reyalyhealthtracker.common.components.FoodTable
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.helpers.changeDate
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.screens.lunch.components.AddLunchModal
import com.reyaly.reyalyhealthtracker.screens.lunch.components.EditLunchModal
import com.reyaly.reyalyhealthtracker.screens.lunch.components.LunchStats
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import java.time.LocalDate

@Composable
fun LunchScreen(
    onDashboardClick: () -> Unit,
    onFoodClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LunchViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.uiState.collectAsState()

    val openAddDialog = remember { mutableStateOf(false) }

    val openEditDialog = remember { mutableStateOf(false) }

    val foodDeleted = remember { mutableStateOf(false) }

    val editClicked = remember { mutableStateOf(false) }

    val spinnerColor = if (isSystemInDarkTheme()) {
        sky_blue
    } else {
        dark_sky_blue
    }

    val foodItemToEdit =  remember { mutableStateOf(
        FoodItem(
            documentId = "",
            meal = "",
            name = "",
            calories = "",
            protein = "",
            fat = "",
            carbs = "",
            quantity = ""
        )
    ) }

    var date = remember { mutableStateOf(LocalDate.now() ) }

    suspend fun onDateChange(direction: String) {
        date = changeDate(date, direction)
        viewModel.getUsersMeals(date.value)
    }

    suspend fun deleteFood(foodItem: FoodItem) {
        viewModel.onDeleteFoodInDates(foodItem, date.value)
        foodDeleted.value = true
    }

    LaunchedEffect(key1 = viewModel, key2 = foodDeleted.value) {
        viewModel.getUsersMeals(date.value)
        Log.d("Lunchscreen", "we in here")
        foodDeleted.value = false
    }

    LaunchedEffect(key1 = editClicked.value) {
        if (editClicked.value) {
            openEditDialog.value = true
            Log.d("lunchscreen", "edit was clicked")
            Log.d("lunchscreen", foodItemToEdit.toString())
        }
    }

    AddLunchModal(
        openDialog = openAddDialog,
        date = date
    )

    EditLunchModal(
        openDialog = openEditDialog,
        editClicked = editClicked,
        date = date,
        foodItemToEdit = foodItemToEdit
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
//        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = modifier) {
            LogoBanner()

            DateSelector(
                initialDate = date,
                onChange = ::onDateChange
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                BasicButton(text = R.string.text_dashboard, modifier = modifier, action = { onDashboardClick() })
                Spacer(modifier = modifier.padding(5.dp))
                BasicButton(text = R.string.nav_food, modifier = modifier, action = { onFoodClick() })
            }
        }

//        DateSelector()

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.padding(20.dp))
            Image(
                painter = painterResource(R.drawable.ic_lunch),
                contentDescription = "lunch",
                modifier = modifier
                    .width(150.dp)
                    .padding(horizontal = 5.dp)
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                uiState.foodsAreLoading -> {
                    ContentSection(
                        contentComposable = {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = modifier.padding(10.dp))
                                CircularProgressIndicator(
                                    color = spinnerColor
                                )
                                Spacer(modifier = modifier.padding(10.dp))
                            }
                                            },
                        text = R.string.lunch_items
                    )
                }
                uiState.foodList.isNotEmpty() -> {
                    ContentSection(
                        contentComposable = {
                            FoodTable(uiState.foodList, editClicked, foodItemToEdit, ::deleteFood)
                                            },
                        text = R.string.lunch_items
                    )
                }
                else -> {
                    ContentSection(
                        contentComposable = {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    stringResource(R.string.food_no_foods),
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp
                                )
                            }
                        },
                        text = R.string.lunch_items
                    )
                }
            }

            Spacer(modifier = modifier.padding(15.dp))
            BasicButton(
                text = R.string.add_food,
                modifier = modifier.padding(10.dp),
                action = { openAddDialog.value = true }
            )
        }

        ContentSection(
            contentComposable = { LunchStats() },
            text = R.string.lunch_stats
        )

        Spacer(modifier = modifier.padding(20.dp))
    }
}

@Preview
@Composable
fun LunchScreenPreview() {
    LunchScreen(
        onDashboardClick = {},
        onFoodClick = {}
    )
}