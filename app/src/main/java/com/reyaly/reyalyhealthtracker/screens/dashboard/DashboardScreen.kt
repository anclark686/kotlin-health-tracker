package com.reyaly.reyalyhealthtracker.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.screens.dashboard.components.DashboardInfo
import com.reyaly.reyalyhealthtracker.common.components.DateSelector
import com.reyaly.reyalyhealthtracker.screens.dashboard.components.TrackerButtons
import com.reyaly.reyalyhealthtracker.storage.date.checkIfDateExists
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.screens.AppViewModel

@Composable
fun DashboardScreen(
    onExerciseClick: () -> Unit,
    onFoodClick: () -> Unit,
    onMedClick: () -> Unit,
    onWaterClick: () -> Unit,
    onWeightClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    var backgroundColor: Color
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        dividerColor = med_sky_blue
    } else {
        backgroundColor = light_sky_blue
        dividerColor = dark_sky_blue
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.getUser()
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
                    text = "Welcome ${viewModel.user?.firstName}!",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            HorizontalDivider(
                modifier = modifier
                    .fillMaxWidth(),
                thickness = 2.dp,
                color = dividerColor
            )
        }

//        DateSelector()

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
        Spacer(modifier = modifier.padding(20.dp))
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