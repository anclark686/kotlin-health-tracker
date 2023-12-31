package com.reyaly.reyalyhealthtracker.screens.dashboard

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.screens.dashboard.components.DashboardInfo
import com.reyaly.reyalyhealthtracker.screens.dashboard.components.TrackerButtons
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DashboardScreen(
    onExerciseClick: () -> Unit,
    onFoodClick: () -> Unit,
    onMedClick: () -> Unit,
    onWaterClick: () -> Unit,
    onWeightClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var backgroundColor: Color
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        dividerColor = med_sky_blue
    } else {
        backgroundColor = light_sky_blue
        dividerColor = dark_sky_blue
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoBanner()

        Column(
            modifier = modifier
                .background(color = backgroundColor)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column (
                modifier = modifier
                    .padding(start = 10.dp, top = 50.dp, end = 10.dp, bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Welcome Alycia!",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Divider(
                modifier = modifier
                    .fillMaxWidth(),
                thickness = 2.dp,
                color = dividerColor
            )
        }

        ContentSection(
            text = R.string.dashboard_stats,
            contentComposable = { DashboardInfo() }
        )

        ContentSection(
            text = R.string.text_trackers,
            contentComposable = {
                TrackerButtons(
                    onExerciseClick = { onExerciseClick() },
                    onFoodClick = { onFoodClick() },
                    onMedClick = { onMedClick() },
                    onWaterClick = { onWaterClick() },
                    onWeightClick = { onWeightClick() }
                )
            }
        )
    }
}

@Preview
@Composable
fun DashboardPreview() {
    DashboardScreen(
        onExerciseClick = { },
        onFoodClick = { },
        onMedClick = { },
        onWaterClick = { },
        onWeightClick = { },
        modifier = Modifier
    )
}