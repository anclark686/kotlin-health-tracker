package com.reyaly.reyalyhealthtracker.screens.exercise

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.common.components.DateSelector
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.DashboardButton
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.helpers.changeDate
import com.reyaly.reyalyhealthtracker.screens.exercise.components.CardioExercises
import com.reyaly.reyalyhealthtracker.screens.exercise.components.ExerciseStats
import com.reyaly.reyalyhealthtracker.screens.exercise.components.StrengthExercises
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import java.time.LocalDate

@Composable
fun ExerciseScreen(
    onDashboardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    val openAddModal = remember { mutableStateOf(false) }

    var date = remember { mutableStateOf(LocalDate.now() ) }

    fun onDateChange(direction: String) {
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

        Column(
            modifier = modifier.padding(top = 20.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(
                text = R.string.exercise_add,
                modifier = modifier,
                action = { openAddModal.value = true}
            )
        }

        ContentSection(
            contentComposable = { CardioExercises() },
            text = R.string.exercise_cardio
        )

        ContentSection(
            contentComposable = { StrengthExercises() },
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