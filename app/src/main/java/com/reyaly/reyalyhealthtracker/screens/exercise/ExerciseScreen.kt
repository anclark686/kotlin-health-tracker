package com.reyaly.reyalyhealthtracker.screens.exercise

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.common.components.DateSelector
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.DashboardButton
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.helpers.changeDate
import com.reyaly.reyalyhealthtracker.screens.exercise.components.ExerciseStats
import com.reyaly.reyalyhealthtracker.screens.med.components.WorkoutCard
import java.time.LocalDate

@Composable
fun ExerciseScreen(
    onDashboardClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExerciseViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.uiState.collectAsState()

    var date = remember { mutableStateOf(LocalDate.now() ) }

    suspend fun onDateChange(direction: String) {
        date = changeDate(date, direction)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = modifier) {
            LogoBanner()

            DateSelector(
                initialDate = date,
                onChange = ::onDateChange
            )

            DashboardButton(modifier = modifier, onDashboardClick = { onDashboardClick() })
        }

        ContentSection(
            contentComposable = { WorkoutCard("cardio", uiState.cardioTimes, date) },
            text = R.string.exercise_cardio
        )

        ContentSection(
            contentComposable = { WorkoutCard("strength", uiState.strengthTimes, date) },
            text = R.string.exercise_strength
        )

        ContentSection(
            contentComposable = { ExerciseStats() },
            text = R.string.exercise_stats
        )

        Spacer(modifier = modifier.padding(20.dp))
    }
}

@Preview
@Composable
fun ExercisePreview() {
    ExerciseScreen(
        onDashboardClick = {},
        modifier = Modifier
    )
}