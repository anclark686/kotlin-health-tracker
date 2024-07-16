package com.reyaly.reyalyhealthtracker.screens.med.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.model.Medication
import com.reyaly.reyalyhealthtracker.screens.med.MedViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale

@Composable
fun MoreMedInfoModal(
    openDialog: MutableState<Boolean>,
    selectedMed: MutableState<Medication>,
    deleteClicked: MutableState<Boolean>,
    editClicked: MutableState<Boolean>,
    date: MutableState<LocalDate>,
    modifier: Modifier = Modifier,
    viewModel: MedViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val dialogWidth = 300.dp
    val dialogHeight = 550.dp

    var dialogColor: Color
    var deleteColor: Color
    var deleteTextColor: Color
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        dialogColor = dark_sky_blue
        deleteColor = errorPink
        dividerColor = med_sky_blue
        deleteTextColor = Color.Black
    } else {
        dialogColor = light_sky_blue
        deleteColor = errorDarkRed
        dividerColor = dark_sky_blue
        deleteTextColor = Color.White
    }

    val openAddModal = remember { mutableStateOf(false) }
    val edit = remember { mutableStateOf(false) }

    fun onDismissModal() {
        openDialog.value = false

        if (!edit.value) {
            selectedMed.value = Medication()
        }
    }

    fun openEditModal() {
        openAddModal.value = true
        edit.value = true
        onDismissModal()
    }

    AddEditMedModal(date, openAddModal, edit = edit, medicationToEdit = selectedMed, editClicked = editClicked)

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
                    Text(
                        text = "${stringResource(R.string.med_info_more)} ${selectedMed.value.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = dividerColor
                )

                Spacer(modifier = modifier.padding(10.dp))

                Column(
                    modifier = modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.med_input_med_name)}:",
                            fontSize = 18.sp,
                        )
                        Text(
                            text = selectedMed.value.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            },
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.padding(5.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.med_input_med_dose)}:",
                            fontSize = 18.sp,
                        )
                        Text(
                            text = selectedMed.value.dose,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.padding(5.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.med_taken)}:",
                            fontSize = 18.sp,
                        )
                        Text(
                            text = if (selectedMed.value.taken!!) "Yes" else "No",
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.padding(5.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.med_times)}:",
                            fontSize = 18.sp,
                        )
                        Text(
                            text = selectedMed.value.times.joinToString(),
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.padding(5.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.med_taken_for)}:",
                            fontSize = 18.sp,
                        )
                        Text(
                            text = selectedMed.value.takenFor.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            },
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.padding(5.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.med_prescriber)}:",
                            fontSize = 18.sp,
                        )
                        Text(
                            text = selectedMed.value.prescriber.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            },
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.padding(5.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.med_last_filled)}:",
                            fontSize = 18.sp,
                        )
                        Text(
                            text = selectedMed.value.lastFilled,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = modifier.padding(5.dp))

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
                            BasicTextButton(
                                text = R.string.med_delete_med,
                                modifier = modifier
                                    .padding(5.dp)
                                    .width(110.dp)
                                    .background(color = deleteColor, RoundedCornerShape(50.dp)),
                                action = { coroutineScope.launch {
                                    viewModel.deleteMed(selectedMed.value)
                                    deleteClicked.value = true
                                } },
                                color = deleteTextColor
                            )
                            BasicTextButton(
                                text = R.string.med_edit_med,
                                modifier = modifier
                                    .padding(5.dp)
                                    .width(110.dp)
                                    .background(color = med_sky_blue, RoundedCornerShape(50.dp)),
                                action = { openEditModal() },
                                color = Color.White
                            )
                        }
                    }
                }

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
                    }
                }
            }
        }
    }
}