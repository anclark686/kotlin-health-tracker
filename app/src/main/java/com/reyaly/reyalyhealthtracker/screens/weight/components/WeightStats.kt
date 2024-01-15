package com.reyaly.reyalyhealthtracker.screens.weight.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun WeightStats(
    modifier: Modifier = Modifier
) {
    var labelColor: Color
    var spinnerColor: Color

    if (isSystemInDarkTheme()) {
        labelColor = light_sky_blue
        spinnerColor = sky_blue
    } else {
        labelColor = dark_sky_blue
        spinnerColor = dark_sky_blue
    }

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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.weight_curr_weight),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.weight_goal_highest),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.weight_goal_lowest),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.weight_goal_lost),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.weight_goal_gained),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.weight_BMI),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.weight_fat),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.weight_ideal),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
                Text(
                    text = "**SOMETHING**",
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = modifier.padding(8.dp))
        }
    }
}

@Preview
@Composable
fun WeightStatsPreview() {
    WeightStats(

    )
}