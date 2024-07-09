package com.reyaly.reyalyhealthtracker.screens.water.components

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.screens.water.WaterViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun WaterStats(
    modifier: Modifier = Modifier,
    viewModel: WaterViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val waterState by viewModel.waterState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

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
        viewModel.getTodaysWater()
        Log.d("water", "you reloading?")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
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
                    text = stringResource(R.string.water_recommended),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Text(
                    text = "${uiState.recommended} oz",
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
                    text = stringResource(R.string.water_total),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Text(
                    text = "${uiState.totalOunces} oz",
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
                    text = stringResource(R.string.water_total_cups),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Text(
                    text = "${uiState.totalCups} cups",
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
                    text = stringResource(R.string.water_left),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Text(
                    text = "${uiState.ouncesRemaining} oz",
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
                    text = stringResource(R.string.water_percentage),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Text(
                    text = uiState.percentage!!,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun WaterStatsPreview() {
    WaterStats(

    )
}