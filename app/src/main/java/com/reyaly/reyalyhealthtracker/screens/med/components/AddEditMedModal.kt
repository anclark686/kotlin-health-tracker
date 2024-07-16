package com.reyaly.reyalyhealthtracker.screens.med.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.common.composable.DateField
import com.reyaly.reyalyhealthtracker.model.Medication
import com.reyaly.reyalyhealthtracker.screens.med.MedViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMedModal(
    date: MutableState<LocalDate>,
    openDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    edit: MutableState<Boolean> = mutableStateOf(false),
    medicationToEdit: MutableState<Medication> = mutableStateOf(Medication()),
    editClicked: MutableState<Boolean> = mutableStateOf(false),
    viewModel: MedViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val medState by viewModel.medState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val dialogWidth = 300.dp
    val dialogHeight = 550.dp

    var dialogColor: Color
    var errorColor: Color
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        dialogColor = dark_sky_blue
        errorColor = errorPink
        dividerColor = med_sky_blue
    } else {
        dialogColor = light_sky_blue
        errorColor = errorPink
        dividerColor = dark_sky_blue
    }

    if (
        edit.value &&
        medState.name.isBlank() &&
        medState.dose.isBlank() &&
        medState.takenFor.isBlank() &&
        medState.prescriber.isBlank() &&
        medState.lastFilled.isBlank() &&
        !uiState.morning &&
        !uiState.afternoon &&
        !uiState.evening &&
        !uiState.night
    ) {
        viewModel.populateFieldsWithInitialValues(medicationToEdit.value)
    }

    fun onDismissModal() {
        openDialog.value = false
        edit.value = false
        medicationToEdit.value = Medication()
        viewModel.clearEverything()
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { onDismissModal() }) {
            Column(
                modifier = modifier
                    .size(dialogWidth, dialogHeight)
                    .background(dialogColor, RoundedCornerShape(8.dp))
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_meds),
                        contentDescription = "meds",
                        modifier = modifier
                            .width(75.dp)
                            .padding(15.dp),
                    )
                    if (!edit.value) {
                        Text(
                            stringResource(R.string.med_add_new),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    } else {
                        Text(
                            "${stringResource(R.string.med_edit_med)} ${medicationToEdit.value.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            }}:",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = dividerColor
                )

                Spacer(modifier = modifier.padding(10.dp))

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
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    if (uiState.timesError?.isNotBlank() == true) {
                        Text(stringResource(id = R.string.med_times))
                        Text(
                            text = uiState.timesError!!,
                            color = errorColor
                        )
                    } else {
                        Text(stringResource(id = R.string.med_times))
                    }

                    Row(modifier = modifier) {
                        Column(
                            modifier = modifier.weight(.5f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Checkbox(
                                    checked = uiState.morning,
                                    onCheckedChange = { viewModel.onMorningChange(it) }
                                )
                                Text(
                                    stringResource(id = R.string.med_morning),
                                    Modifier.clickable { viewModel.onMorningChange(!uiState.morning) }
                                )
                            }
                        }

                        Column(
                            modifier = modifier.weight(.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Checkbox(
                                    checked = uiState.afternoon,
                                    onCheckedChange = { viewModel.onAfternoonChange(it) }
                                )
                                Text(
                                    stringResource(id = R.string.med_afternoon),
                                    Modifier.clickable { viewModel.onAfternoonChange(!uiState.afternoon) }
                                )
                            }
                        }
                    }

                    Row(modifier = modifier) {
                        Column(
                            modifier = modifier.weight(.5f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Checkbox(
                                    checked = uiState.evening,
                                    onCheckedChange = { viewModel.onEveningChange(it) }
                                )
                                Text(
                                    stringResource(id = R.string.med_evening),
                                    Modifier.clickable { viewModel.onEveningChange(!uiState.evening) }
                                )
                            }
                        }

                        Column(
                            modifier = modifier.weight(.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End) {
                                Checkbox(
                                    checked = uiState.night,
                                    onCheckedChange = { viewModel.onNightChange(it) }
                                )
                                Text(
                                    stringResource(id = R.string.med_night),
                                    Modifier.clickable { viewModel.onNightChange(!uiState.night) }
                                )
                            }
                        }
                    }
                }


                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasicField(
                        text = R.string.med_prescriber,
                        value = medState.prescriber,
                        onNewValue = viewModel::onPrescriberChange,
                        errorMsg = uiState.prescriberError,
                    )
                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasicField(
                        text = R.string.med_taken_for,
                        value = medState.takenFor,
                        onNewValue = viewModel::onTakenForChange,
                        errorMsg = uiState.takenForError,
                    )
                }

                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DateField(
                        text = R.string.med_last_filled,
                        value = medState.lastFilled,
                        onNewValue = viewModel::onLastFilledChange,
                        errorMsg = uiState.lastFilledError,
                    )
                }
                Spacer(modifier = modifier.padding(10.dp))
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = dividerColor
                )

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
                            action = { onDismissModal() }
                        )
                        BasicButton(
                            text = R.string.add,
                            modifier = modifier
                                .padding(5.dp)
                                .width(100.dp),
                            action = {
                                coroutineScope.launch {
                                    if (!edit.value) {
                                        if (viewModel.onAddNewMed(date.value)) {
                                            onDismissModal()
                                            viewModel.getUsersMeds(date.value)
                                        }
                                    } else {
                                        if (viewModel.onEditMed(medicationToEdit.value, date.value)) {
                                            onDismissModal()
                                            editClicked.value = true
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}