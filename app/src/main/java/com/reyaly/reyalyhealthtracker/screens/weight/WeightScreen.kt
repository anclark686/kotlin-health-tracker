package com.reyaly.reyalyhealthtracker.screens.weight

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.DashboardButton
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.screens.intake.components.BasicInfo
import com.reyaly.reyalyhealthtracker.screens.intake.components.HealthInfo
import com.reyaly.reyalyhealthtracker.screens.weight.components.AddWeightModal
import com.reyaly.reyalyhealthtracker.screens.weight.components.GoalTracker
import com.reyaly.reyalyhealthtracker.screens.weight.components.HistoricalWeightData
import com.reyaly.reyalyhealthtracker.screens.weight.components.ProgressChart
import com.reyaly.reyalyhealthtracker.screens.weight.components.WeightStats
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun WeightScreen(
    onDashboardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    val openAddModal = remember { mutableStateOf(false) }

    AddWeightModal(
        openDialog = openAddModal,
    )

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
            DashboardButton(modifier = modifier, onDashboardClick = { onDashboardClick() })
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.padding(20.dp))
            Image(
                painter = painterResource(R.drawable.ic_scale),
                contentDescription = "lunch",
                modifier = modifier
                    .width(150.dp)
                    .padding(horizontal = 5.dp)
            )
        }

        Column(
            modifier = modifier.padding(top = 20.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(
                text = R.string.weight_add,
                modifier = modifier,
                action = { openAddModal.value = true}
            )
        }

        ContentSection(
            contentComposable = { ProgressChart() },
            text = R.string.weight_chart
        )

        ContentSection(
            contentComposable = { GoalTracker() },
            text = R.string.weight_goal_tracker
        )

        ContentSection(
            contentComposable = { HistoricalWeightData() },
            text = R.string.weight_historical
        )

        ContentSection(
            contentComposable = { WeightStats() },
            text = R.string.weight_stats
        )

        Spacer(modifier = modifier.padding(20.dp))
    }
}

@Preview
@Composable
fun WeightPreview() {
    WeightScreen(
        onDashboardClick = {},
        modifier = Modifier
    )
}