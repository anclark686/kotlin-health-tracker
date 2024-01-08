package com.reyaly.reyalyhealthtracker.screens.water.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.reyaly.reyalyhealthtracker.R

@Composable
fun WaterChart(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = stringResource(R.string.text_weight),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview
@Composable
fun WaterChartPreview() {
    WaterChart(

    )
}