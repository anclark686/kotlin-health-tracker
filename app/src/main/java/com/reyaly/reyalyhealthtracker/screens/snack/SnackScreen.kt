package com.reyaly.reyalyhealthtracker.screens.snack

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
import com.reyaly.reyalyhealthtracker.screens.snack.components.AddSnackModal
import com.reyaly.reyalyhealthtracker.screens.snack.components.SnackStats
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import java.time.LocalDate
import java.util.Locale

@Composable
fun SnackScreen(
    onDashboardClick: () -> Unit,
    onFoodClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SnackViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.uiState.collectAsState()

    // need to add a screen/button for old foods

    val openDialog = remember { mutableStateOf(false) }

    var date = remember { mutableStateOf(LocalDate.now() ) }

    suspend fun onDateChange(direction: String) {
        date = changeDate(date, direction)
        viewModel.getUsersMeals(date.value)
    }

    var spinnerColor: Color

    if (isSystemInDarkTheme()) {
        spinnerColor = sky_blue
    } else {
        spinnerColor = dark_sky_blue
    }

    LaunchedEffect(key1 = viewModel) {
        Log.d("snackScreen", "you reloading?")
        viewModel.getUsersMeals(date.value)
        Log.d("Snackscreen", "we in here")
    }

    val something = uiState.foodList

    AddSnackModal(
        openDialog = openDialog,
        date = date
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

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.padding(20.dp))
            Image(
                painter = painterResource(R.drawable.ic_snack),
                contentDescription = "snack",
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
                                CircularProgressIndicator(
                                    color = spinnerColor
                                )
                            }
                                            },
                        text = R.string.snack_items
                    )
                }
                uiState.foodList.isNotEmpty() -> {
                    ContentSection(
                        contentComposable = { FoodTable(foodList = uiState.foodList) },
                        text = R.string.snack_items
                    )
                    Text(
                        modifier = modifier
                            .padding(vertical = 2.dp),
                        text = uiState.foodList.toString(),
                        textAlign = TextAlign.Center,
                    )
                    uiState.foodList.forEachIndexed { index, item ->
                        Text(
                            modifier = modifier
                                .padding(vertical = 2.dp),
                            text = item.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            },
                            textAlign = TextAlign.Center,
                        )
                    }
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
                        text = R.string.snack_items
                    )
                }
            }

            Spacer(modifier = modifier.padding(15.dp))
            BasicButton(
                text = R.string.add_food,
                modifier = modifier.padding(10.dp),
                action = { openDialog.value = true }
            )
        }

        ContentSection(
            contentComposable = { SnackStats() },
            text = R.string.snack_stats
        )

        Spacer(modifier = modifier.padding(20.dp))
    }
}

@Preview
@Composable
fun SnackScreenPreview() {
    SnackScreen(
        onDashboardClick = {},
        onFoodClick = {}
    )
}