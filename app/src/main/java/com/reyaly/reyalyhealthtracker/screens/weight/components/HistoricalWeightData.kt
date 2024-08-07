package com.reyaly.reyalyhealthtracker.screens.weight.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicExposedDropdown
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.screens.weight.WeightViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun HistoricalWeightData(
    modifier: Modifier = Modifier,
    viewModel: WeightViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val weightState by viewModel.weightState.collectAsState()

    var labelColor: Color
    var spinnerColor: Color
    var buttonBackground: Color
    var buttonText: Color

    if (isSystemInDarkTheme()) {
        labelColor = light_sky_blue
        spinnerColor = sky_blue
        buttonBackground = med_sky_blue
        buttonText = Color.White
    } else {
        labelColor = dark_sky_blue
        spinnerColor = dark_sky_blue
        buttonBackground = sky_blue
        buttonText = Color.Black
    }

    val openAddModal = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel) {
        viewModel.getHistoricalData()
        Log.d("weightScreen", "you reloading?")
    }

    AddWeightModal(
        openDialog = openAddModal,
        edit = true
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!uiState.valuesAreLoading) {
            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BasicExposedDropdown(
                        text = R.string.dates,
                        list = uiState.historicalDates.toList(),
                        onNewValue = viewModel::changeHistoricalWeightValue,
                    )
                }
                Spacer(modifier = modifier.padding(5.dp))
                if (uiState.historicalDate!!.isNotBlank()) {
                    if (uiState.historicalWeight!!.isNotBlank()) {
                        Row (
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${stringResource(R.string.text_weight)} for ${uiState.historicalDate}:",
                                fontSize = 18.sp,
                                color = labelColor
                            )
                            Text(
                                text = "${uiState.historicalWeight!!} lbs",
                                fontSize = 18.sp
                            )
                        }
                    } else {
                        Row (
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${stringResource(R.string.weight_none)} for ${uiState.historicalDate}:",
                                fontSize = 18.sp,
                                color = labelColor
                            )
                        }
                    }

                    Spacer(modifier = modifier.padding(5.dp))

                    Row(
                        modifier = modifier
                    ) {
                        BasicTextButton(
                            text = R.string.weight_add_full,
                            modifier = modifier
                                .background(color = buttonBackground, RoundedCornerShape(50.dp)),
                            color = buttonText,
                            action = { openAddModal.value = true },
                        )
                    }
                } else {
                    Row (
                        modifier = modifier.fillMaxWidth(.6f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.select_date),
                            fontSize = 18.sp,
                            color = labelColor
                        )
                    }
                }
            }
            Spacer(modifier = modifier.padding(8.dp))
        } else {
            Spacer(modifier = modifier.padding(10.dp))
            CircularProgressIndicator(
                color = spinnerColor
            )
            Spacer(modifier = modifier.padding(10.dp))
        }
    }
}