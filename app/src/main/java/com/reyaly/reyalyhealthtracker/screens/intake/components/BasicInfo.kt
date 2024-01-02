package com.reyaly.reyalyhealthtracker.screens.intake.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicExposedDropdown
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.screens.intake.IntakeViewModel

@Composable
fun BasicInfo(
    modifier: Modifier = Modifier,
    viewModel: IntakeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val sexItems = listOf(
        stringResource(R.string.intake_dropdown_sex_male),
        stringResource(R.string.intake_dropdown_sex_female),
    )

    val genderItems = listOf(
        stringResource(R.string.intake_dropdown_gender_male),
        stringResource(R.string.intake_dropdown_gender_female),
        stringResource(R.string.intake_dropdown_gender_non_binary),
        stringResource(R.string.intake_dropdown_gender_agender),
        stringResource(R.string.intake_dropdown_gender_other),
    )

    val heightItems = listOf(
        stringResource(R.string.intake_dropdown_height_4_0),
        stringResource(R.string.intake_dropdown_height_4_1),
        stringResource(R.string.intake_dropdown_height_4_2),
        stringResource(R.string.intake_dropdown_height_4_3),
        stringResource(R.string.intake_dropdown_height_4_4),
        stringResource(R.string.intake_dropdown_height_4_5),
        stringResource(R.string.intake_dropdown_height_4_6),
        stringResource(R.string.intake_dropdown_height_4_7),
        stringResource(R.string.intake_dropdown_height_4_8),
        stringResource(R.string.intake_dropdown_height_4_9),
        stringResource(R.string.intake_dropdown_height_4_10),
        stringResource(R.string.intake_dropdown_height_4_11),
        stringResource(R.string.intake_dropdown_height_5_0),
        stringResource(R.string.intake_dropdown_height_5_1),
        stringResource(R.string.intake_dropdown_height_5_2),
        stringResource(R.string.intake_dropdown_height_5_3),
        stringResource(R.string.intake_dropdown_height_5_4),
        stringResource(R.string.intake_dropdown_height_5_5),
        stringResource(R.string.intake_dropdown_height_5_6),
        stringResource(R.string.intake_dropdown_height_5_7),
        stringResource(R.string.intake_dropdown_height_5_8),
        stringResource(R.string.intake_dropdown_height_5_9),
        stringResource(R.string.intake_dropdown_height_5_10),
        stringResource(R.string.intake_dropdown_height_5_11),
        stringResource(R.string.intake_dropdown_height_6_0),
        stringResource(R.string.intake_dropdown_height_6_1),
        stringResource(R.string.intake_dropdown_height_6_2),
        stringResource(R.string.intake_dropdown_height_6_3),
        stringResource(R.string.intake_dropdown_height_6_4),
        stringResource(R.string.intake_dropdown_height_6_5),
        stringResource(R.string.intake_dropdown_height_6_6),
        stringResource(R.string.intake_dropdown_height_6_7),
        stringResource(R.string.intake_dropdown_height_6_8),
        stringResource(R.string.intake_dropdown_height_6_9),
        stringResource(R.string.intake_dropdown_height_6_10),
        stringResource(R.string.intake_dropdown_height_6_11),
        stringResource(R.string.intake_dropdown_height_7_0),
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
                text = R.string.intake_input_first_name,
                value = uiState.firstName,
                onNewValue = viewModel::onFirstNameChange,
                errorMsg = uiState.firstNameError,
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicField(
                text = R.string.intake_input_last_name,
                value = uiState.lastName,
                onNewValue = viewModel::onLastNameChange,
                errorMsg = uiState.lastNameError,
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicField(
                text = R.string.intake_input_phone,
                value = uiState.phoneNum,
                onNewValue = viewModel::onPhoneNumChange,
                errorMsg = uiState.phoneNumError,
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicField(
                text = R.string.intake_input_birthday,
                value = uiState.birthday,
                onNewValue = viewModel::onBirthdayChange,
                errorMsg = uiState.birthdayError,
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicExposedDropdown(
                text = R.string.intake_input_height,
                list = heightItems,
                onNewValue = viewModel::onHeightChange,
                errorMsg = uiState.heightError,
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicExposedDropdown(
                text = R.string.intake_input_sex,
                list = sexItems,
                onNewValue = viewModel::onSexChange,
                errorMsg = uiState.sexError,
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicExposedDropdown(
                text = R.string.intake_input_gender,
                list = genderItems,
                onNewValue = viewModel::onGenderChange,
                errorMsg = uiState.genderError,
            )
        }
    }
}

@Preview
@Composable
fun BasicInfoFormPreview() {
    BasicInfo()
}