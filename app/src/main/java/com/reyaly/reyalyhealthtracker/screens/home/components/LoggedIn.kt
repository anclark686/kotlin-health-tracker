package com.reyaly.reyalyhealthtracker.screens.home.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue

@Composable
fun LoggedIn(
    onDashboardClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var shadowColor: Color

    if (isSystemInDarkTheme()) {
        shadowColor = Color.LightGray
    } else {
        shadowColor = Color.Black
    }

    Column(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(5.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = stringResource(R.string.home_dashboard),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Button(
                modifier = modifier,
                onClick = { onDashboardClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = med_sky_blue,
                    contentColor = Color.White
                ),

            ) {
                Text(stringResource(R.string.text_dashboard), fontSize = 30.sp)
            }
        }
    }
}

@Preview
@Composable
fun LoggedInPreview() {
    LoggedIn(
        onDashboardClick = { },
        modifier = Modifier.fillMaxSize()
    )
}