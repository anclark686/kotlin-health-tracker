package com.reyaly.reyalyhealthtracker.screens.weight.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.screens.weight.WeightViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun WeightStats(
    modifier: Modifier = Modifier,
    viewModel: WeightViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val weightState by viewModel.weightState.collectAsState()

    var labelColor: Color
    var spinnerColor: Color

    if (isSystemInDarkTheme()) {
        labelColor = light_sky_blue
        spinnerColor = sky_blue
    } else {
        labelColor = dark_sky_blue
        spinnerColor = dark_sky_blue
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.getWeightStats()
        Log.d("weightScreen", "you reloading?")
    }

    if (!uiState.valuesAreLoading) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween,
//        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.weight_init_weight),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = uiState.initialWeight!!,
                        fontSize = 18.sp
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.weight_curr_weight),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = uiState.currentWeight!!,
                        fontSize = 18.sp
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.weight_goal_highest),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = uiState.highestWeight!!,
                        fontSize = 18.sp
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.weight_goal_lowest),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = uiState.lowestWeight!!,
                        fontSize = 18.sp
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.weight_goal_diff),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = uiState.weightDifference!!,
                        fontSize = 18.sp
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.weight_BMI),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = "**SOMETHING**",
                        fontSize = 18.sp
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.weight_fat),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = "**SOMETHING**",
                        fontSize = 18.sp
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.weight_ideal),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = "**SOMETHING**",
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = modifier.padding(8.dp))
            }
        }
    } else {
        Spacer(modifier = modifier.padding(10.dp))
        CircularProgressIndicator(
            color = spinnerColor
        )
        Spacer(modifier = modifier.padding(10.dp))
    }
}

@Preview
@Composable
fun WeightStatsPreview() {
    WeightStats(

    )
}