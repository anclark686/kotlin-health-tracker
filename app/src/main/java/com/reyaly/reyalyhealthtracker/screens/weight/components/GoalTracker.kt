package com.reyaly.reyalyhealthtracker.screens.weight.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun GoalTracker(
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
//            .fillMaxSize()
//            .background(color = Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier.padding(10.dp)
        ) {
            Text(
                text = stringResource(R.string.weight_on_way),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }

        Image(
            painter = painterResource(R.drawable.ic_goal),
            contentDescription = "logo",
            modifier = modifier
                .width(150.dp)
                .padding(15.dp),
        )

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
                    text = stringResource(R.string.weight_goal_weight),
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
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.weight_score),
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
            modifier = modifier.padding(10.dp)
        ) {
            Row(
                modifier = modifier
            ) {
                Text(
                    text = "${stringResource(R.string.weight_diff)} **SOMETHING**!",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = modifier.padding(10.dp)
        ) {
            Row(
                modifier = modifier
            ) {
                Text(
                    text = "${stringResource(R.string.weight_only)} **SOMETHING** ${stringResource(R.string.weight_to_go)}",
                    fontSize = 20.sp,
                    color = labelColor
                )
            }
            Spacer(modifier = modifier.padding(8.dp))
        }
    }
}

@Preview
@Composable
fun GoalTrackerPreview() {
    GoalTracker(

    )
}