package com.reyaly.reyalyhealthtracker.screens.med.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.screens.med.MedViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import java.time.LocalDate

@Composable
fun MedInfo(
    date: MutableState<LocalDate>,
    modifier: Modifier = Modifier,
    viewModel: MedViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var spinnerColor: Color

    if (isSystemInDarkTheme()) {
        spinnerColor = sky_blue
    } else {
        spinnerColor = dark_sky_blue
    }

    val deleteClicked = remember { mutableStateOf(false) }
    val editClicked = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel, key2 = deleteClicked.value, key3 = editClicked.value) {
        viewModel.getUsersMeds(date.value)

        if (deleteClicked.value) {
            deleteClicked.value = false
        }

        if (editClicked.value) {
            editClicked.value = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        when {
            uiState.medsAreLoading -> {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = modifier.padding(10.dp))
                    CircularProgressIndicator(
                        color = spinnerColor
                    )
                    Spacer(modifier = modifier.padding(10.dp))
                }
            }
            uiState.medList.isEmpty() -> {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        stringResource(R.string.med_no_meds),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                }
            }
            else -> {
                if (uiState.finalMedTimeData.morning.isNotEmpty()) {
                    MedTable(
                        "Morning",
                        uiState.finalMedTimeData.morning,
                        date,
                        deleteClicked,
                        editClicked,
                        viewModel::toggleMed
                    )
                }
                if (uiState.finalMedTimeData.afternoon.isNotEmpty()) {
                    MedTable(
                        "Afternoon",
                        uiState.finalMedTimeData.afternoon,
                        date,
                        deleteClicked,
                        editClicked,
                        viewModel::toggleMed
                    )
                }
                if (uiState.finalMedTimeData.evening.isNotEmpty()) {
                    MedTable(
                        "Evening",
                        uiState.finalMedTimeData.evening,
                        date,
                        deleteClicked,
                        editClicked,
                        viewModel::toggleMed
                    )
                }
                if (uiState.finalMedTimeData.night.isNotEmpty()) {
                    MedTable(
                        "Night",
                        uiState.finalMedTimeData.night,
                        date,
                        deleteClicked,
                        editClicked,
                        viewModel::toggleMed
                    )
                }
            }
        }
    }
}