package com.reyaly.reyalyhealthtracker.screens.exercise.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun CardioExercises(
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
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_cardio),
                contentDescription = "cardio",
                modifier = modifier.width(100.dp)
            )
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
                    text = stringResource(R.string.water_recommended),
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
                    text = stringResource(R.string.water_total),
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
                    text = stringResource(R.string.water_left),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Text(
                    text = "**SOMETHING**",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun CardioExercisesPreview() {
    CardioExercises(

    )
}