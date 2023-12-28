package com.reyaly.reyalyhealthtracker.screens.water

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue

@Composable
fun WaterScreen(
    onDashboardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Button(
                modifier = modifier.padding(25.dp),
                onClick = { onDashboardClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = med_sky_blue,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.text_dashboard), fontSize = 20.sp)
            }
        }
        Text(
            text = stringResource(R.string.text_water),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview
@Composable
fun WaterPreview() {
    WaterScreen(
        onDashboardClick = {},
        modifier = Modifier.fillMaxSize()
    )
}