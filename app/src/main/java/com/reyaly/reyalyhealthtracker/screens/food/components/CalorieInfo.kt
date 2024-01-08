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
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicExposedDropdown
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun CalorieInfo(
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

    val activityLevelItems = listOf(
        stringResource(R.string.intake_dropdown_activity_sedentary),
        stringResource(R.string.intake_dropdown_activity_light),
        stringResource(R.string.intake_dropdown_activity_moderate),
        stringResource(R.string.intake_dropdown_activity_daily),
        stringResource(R.string.intake_dropdown_activity_very),
        stringResource(R.string.intake_dropdown_activity_intense),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicExposedDropdown(
                text = R.string.food_activity_level,
                list = activityLevelItems,
                onNewValue = {},
                errorMsg = null,
            )
        }

//        Column(
//            modifier = modifier
//                .padding(horizontal = 10.dp, vertical = 5.dp)
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = stringResource(R.string.food_activity_level),
//                fontSize = 18.sp,
//                color = labelColor
//            )
//            Text(
//                text = "**SOMETHING**",
//                fontSize = 18.sp
//            )
//            BasicButton(text = R.string.modify, modifier = modifier, action = {})
//        }

        Column(
            modifier = modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row (
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.food_calorie_allowance),
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
                    text = stringResource(R.string.food_calories_used),
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
                    text = stringResource(R.string.food_calories_burned),
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
                    text = stringResource(R.string.food_calories_remaining),
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
fun CalorieInfoPreview() {
    CalorieInfo(

    )
}