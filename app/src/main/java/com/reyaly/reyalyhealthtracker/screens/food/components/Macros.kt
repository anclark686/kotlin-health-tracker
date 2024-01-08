package com.reyaly.reyalyhealthtracker.screens.food.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.reyaly.reyalyhealthtracker.common.composable.BasicExposedDropdown
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun Macros(
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

    val macroChoices = listOf(
        stringResource(R.string.food_balanced),
        stringResource(R.string.food_low_carb),
        stringResource(R.string.food_low_fat),
        stringResource(R.string.food_high_protein),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicExposedDropdown(
                text = R.string.food_diet_choice,
                list = macroChoices,
                onNewValue = {},
                errorMsg = null,
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),

            ) {
            Row (
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.food_macros_protein),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
                Text(
                    text = "**SOMETHING**",
                    fontSize = 18.sp
                )
            }

            Row (
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.food_macros_carbs),
                    fontSize = 18.sp,
                    color = labelColor
                )
                Spacer(modifier = modifier.padding(5.dp))
                Text(
                    text = "**SOMETHING**",
                    fontSize = 18.sp
                )
            }

            Row (
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.food_macros_fat),
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
        Spacer(modifier = modifier.padding(10.dp))
    }
}

@Preview
@Composable
fun MacrosPreview() {
    Macros(

    )
}