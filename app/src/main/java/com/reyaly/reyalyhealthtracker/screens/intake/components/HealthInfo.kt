package com.reyaly.reyalyhealthtracker.screens.intake.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicExposedDropdown
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.screens.intake.IntakeViewModel

@Composable
fun HealthInfo(
    modifier: Modifier = Modifier,
    viewModel: IntakeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val activityLevelItems = listOf(
        stringResource(R.string.intake_dropdown_activity_sedentary),
        stringResource(R.string.intake_dropdown_activity_light),
        stringResource(R.string.intake_dropdown_activity_moderate),
        stringResource(R.string.intake_dropdown_activity_daily),
        stringResource(R.string.intake_dropdown_activity_very),
        stringResource(R.string.intake_dropdown_activity_intense),
    )

    val weightGoalsItems = listOf(
        stringResource(R.string.intake_dropdown_goal_maintain),
        stringResource(R.string.intake_dropdown_goal_mildloss),
        stringResource(R.string.intake_dropdown_goal_weightloss),
        stringResource(R.string.intake_dropdown_goal_extremeloss),
        stringResource(R.string.intake_dropdown_goal_mildgain),
        stringResource(R.string.intake_dropdown_goal_weightgain),
        stringResource(R.string.intake_dropdown_goal_extremegain),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicField(
                text = R.string.intake_input_curr_weight,
                value = uiState.currWeight,
                onNewValue = viewModel::onCurrWeightChange,
                errorMsg = uiState.currWeightError,
            )
        }
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicField(
                text = R.string.intake_input_goal_weight,
                value = uiState.goalWeight,
                onNewValue = viewModel::onGoalWeightChange,
                errorMsg = uiState.goalWeightError,
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicExposedDropdown(
                text = R.string.intake_input_weight_activity,
                list = weightGoalsItems,
                onNewValue = viewModel::onWeightGoalsChange,
                errorMsg = uiState.weightGoalsError,
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicExposedDropdown(
                text = R.string.intake_input_activity_level,
                list = activityLevelItems,
                onNewValue = viewModel::onActivityLevelChange,
                errorMsg = uiState.activityLevelError,
            )
        }
    }
}

@Preview
@Composable
fun HealthInfoPreview() {
    HealthInfo()
}