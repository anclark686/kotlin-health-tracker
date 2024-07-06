package com.reyaly.reyalyhealthtracker.screens.med.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.screens.med.MedViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun MedInfo(
    modifier: Modifier = Modifier,
    viewModel: MedViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val medState by viewModel.medState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var headerColor: Color
    var everyOtherColor: Color
    var borderColor: Color
    var spinnerColor: Color

    if (isSystemInDarkTheme()) {
        headerColor = med_sky_blue
        everyOtherColor = Color.DarkGray
        borderColor = light_sky_blue
        spinnerColor = sky_blue
    } else {
        headerColor = sky_blue
        everyOtherColor = Color.White
        borderColor = dark_sky_blue
        spinnerColor = dark_sky_blue
    }

    var showAddForm = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel) {
        viewModel.getUsersMeds()
        Log.d("medScreen", "you reloading?")
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
            uiState.medList.isNotEmpty() -> {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Medication List",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .background(color = headerColor)

                    ) {
                        Column(
                            modifier = modifier
                                .weight(.5f)
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                stringResource(R.string.med_input_med_name),
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                stringResource(R.string.med_input_med_dose),
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                stringResource(R.string.med_input_med_time),
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    uiState.medList.forEachIndexed { index, item ->
                        Row(
                            modifier = modifier
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth()
                                .background(color = if (index % 2 != 0) everyOtherColor else Color.Unspecified),
                        ) {
                            Column(
                                modifier = modifier
                                    .weight(.5f)
                                    .border(border = BorderStroke(width = 1.dp, borderColor)),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = modifier
                                        .padding(vertical = 2.dp),
                                    text = item.name.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.getDefault()
                                        ) else it.toString()
                                    },
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Column(
                                modifier = modifier
                                    .weight(.25f)
                                    .border(border = BorderStroke(width = 1.dp, borderColor)),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = modifier
                                        .padding(vertical = 2.dp),
                                    text = item.dose,
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Column(
                                modifier = modifier
                                    .weight(.25f)
                                    .border(border = BorderStroke(width = 1.dp, borderColor)),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = modifier
                                        .padding(vertical = 2.dp),
                                    text = item.time,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }
            else -> {
                if (!showAddForm.value) {
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
                } else {
                    Spacer(modifier = modifier.padding(10.dp))
                }
            }
        }

        if (showAddForm.value) {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicField(
                    text = R.string.med_input_med_name,
                    value = medState.name,
                    onNewValue = viewModel::onNameChange,
                    errorMsg = uiState.nameError,
                )
            }

            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicField(
                    text = R.string.med_input_med_dose,
                    value = medState.dose,
                    onNewValue = viewModel::onDoseChange,
                    errorMsg = uiState.doseError,
                )
            }

            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicField(
                    text = R.string.med_input_med_time,
                    value = medState.time,
                    onNewValue = viewModel::onTimeChange,
                    errorMsg = uiState.timeError,
                )
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BasicButton(
                        text = R.string.close,
                        modifier = modifier
                            .padding(5.dp)
                            .width(100.dp),
                        action = { showAddForm.value = false }
                    )
                    BasicButton(
                        text = R.string.add,
                        modifier = modifier
                            .padding(5.dp)
                            .width(100.dp),
                        action = {
                            coroutineScope.launch {
                                viewModel.onAddNewMed()
                            }
                        }
                    )
                }
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BasicButton(
                        text = R.string.med_edit_meds,
                        modifier = modifier
                            .padding(5.dp)
                            .width(140.dp),
                        action = { showAddForm.value =  true }
                    )
                    BasicButton(
                        text = R.string.add_new,
                        modifier = modifier
                            .padding(5.dp)
                            .width(140.dp),
                        action = { showAddForm.value =  true }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MedInfoPreview() {
    MedInfo()
}