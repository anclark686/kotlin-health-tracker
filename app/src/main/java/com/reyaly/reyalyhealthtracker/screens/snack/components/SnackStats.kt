package com.reyaly.reyalyhealthtracker.screens.snack.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun SnackStats(
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
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row (
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.blds_stats_count),
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
                    text = stringResource(R.string.blds_stats_calories),
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
                    text = stringResource(R.string.blds_stats_protein),
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
                    text = stringResource(R.string.blds_stats_fat),
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
                    text = stringResource(R.string.blds_stats_carbs),
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
    }
}

@Preview
@Composable
fun SnackStatsPreview() {
    SnackStats(

    )
}