package com.reyaly.reyalyhealthtracker.screens.med.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.model.Medication
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale
import kotlin.reflect.KSuspendFunction3

@Composable
fun MedTable(
    time: String,
    medList: MutableList<Medication>,
    date: MutableState<LocalDate>,
    deleteClicked: MutableState<Boolean>,
    editClicked: MutableState<Boolean>,
    toggleMed: KSuspendFunction3<Medication, LocalDate, String, Unit>,
    modifier: Modifier = Modifier,
) {
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

    val openMoreInfoModal = remember { mutableStateOf(false) }
    val selectedMed = remember { mutableStateOf(Medication()) }

    val coroutineScope = rememberCoroutineScope()

    fun openModalAndSelectMed(med: Medication) {
        openMoreInfoModal.value = true
        selectedMed.value = med
    }

    MoreMedInfoModal(openMoreInfoModal, selectedMed, deleteClicked, editClicked, date)

    when {
        medList.isNotEmpty() -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$time Medication List",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${medList.count()} Total Medication(s)",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
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
                            stringResource(R.string.med_taken),
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
                            stringResource(R.string.med_info),
                            modifier = modifier
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                medList.forEachIndexed { index, item ->
                    val taken = remember { mutableStateOf(item.taken) }

                    Row(
                        modifier = modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .background(color = if (index % 2 != 0) everyOtherColor else Color.Unspecified)
                    ) {
                        Column(
                            modifier = modifier
                                .weight(.5f)
                                .border(border = BorderStroke(width = 1.dp, borderColor))
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
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
                                .border(border = BorderStroke(width = 1.dp, borderColor))
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
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
                                .border(border = BorderStroke(width = 1.dp, borderColor))
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Checkbox(
                                checked = taken.value!!,
                                onCheckedChange = { coroutineScope.launch {
                                    toggleMed(item, date.value, time.lowercase())
                                    taken.value = !taken.value!!
                                } }
                            )
                        }

                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .border(border = BorderStroke(width = 1.dp, borderColor))
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = modifier.padding(3.dp))
                            BasicTextButton(
                                text = R.string.med_info_plus,
                                modifier = modifier
                                    .background(headerColor, RoundedCornerShape(10.dp))
                                    .size(35.dp, 35.dp),
                                action = { openModalAndSelectMed(item) },
                                fontSize = 15.sp,
                            )
                            Spacer(modifier = modifier.padding(3.dp))
                        }
                    }
                }
            }
        }
    }
}