package com.reyaly.reyalyhealthtracker.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue

@Composable
fun TrackerButtons(
    onExerciseClick: () -> Unit,
    onFoodClick: () -> Unit,
    onMedClick: () -> Unit,
    onWaterClick: () -> Unit,
    onWeightClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(

        ) {
            Button(
                modifier = Modifier.width(150.dp).padding(5.dp),
                onClick = { onWeightClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = med_sky_blue,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.text_weight), fontSize = 20.sp)
            }
            Button(
                modifier = Modifier.width(150.dp).padding(5.dp),
                onClick = { onExerciseClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = med_sky_blue,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.text_exercise), fontSize = 20.sp)
            }
        }

        Row(

        ) {
            Button(
                modifier = Modifier.width(150.dp).padding(5.dp),
                onClick = { onFoodClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = med_sky_blue,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.text_food), fontSize = 20.sp)
            }
            Button(
                modifier = Modifier.width(150.dp).padding(5.dp),
                onClick = { onWaterClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = med_sky_blue,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.text_water), fontSize = 20.sp)
            }
        }


        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = Modifier.width(170.dp).padding(5.dp),
                onClick = { onMedClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = med_sky_blue,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.text_med), fontSize = 20.sp)
            }
        }
    }
}

@Preview
@Composable
fun TrackerButtonsPreview() {
    TrackerButtons(
        onExerciseClick = { },
        onFoodClick = { },
        onMedClick = { },
        onWaterClick = { },
        onWeightClick = { },
    )
}