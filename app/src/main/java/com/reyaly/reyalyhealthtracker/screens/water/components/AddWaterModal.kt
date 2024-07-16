package com.reyaly.reyalyhealthtracker.screens.water.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.reyaly.reyalyhealthtracker.common.composable.BasicExposedDropdown
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.screens.water.WaterViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import kotlinx.coroutines.launch

@Composable
fun AddWaterModal(
    openDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    edit: Boolean = false,
    viewModel: WaterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val waterState by viewModel.waterState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val dialogWidth = 300.dp
    val dialogHeight = 400.dp

    var dialogColor: Color
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        dialogColor = dark_sky_blue
        dividerColor = med_sky_blue
    } else {
        dialogColor = light_sky_blue
        dividerColor = dark_sky_blue
    }

    val cupsOrOunces = remember { mutableStateOf(true) }
    val cups = (1..10).map{x -> x.toString()}

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            Column(
                modifier = modifier
                    .size(dialogWidth, dialogHeight)
                    .background(dialogColor, RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_water),
                        contentDescription = "meds",
                        modifier = modifier
                            .width(75.dp)
                            .padding(15.dp),
                    )
                    if (!edit) {
                        Text(
                            stringResource(R.string.water_some),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    } else {
                        Text(
                            "${stringResource(R.string.water_add)} for ${uiState.historicalDate}:",
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

                if (!cupsOrOunces.value) {
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BasicTextButton(
                            text = R.string.water_add_cups,
                            modifier = modifier.fillMaxWidth(),
                            action = { cupsOrOunces.value = !cupsOrOunces.value }
                        )
                    }

                    Column(
                        modifier = modifier
                    ) {
                        BasicField(
                            text = R.string.water_ounces,
                            value = waterState.ounces,
                            onNewValue = viewModel::onOzChange,
                        )
                    }
                } else {
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BasicTextButton(
                            text = R.string.water_add_ounces,
                            modifier = modifier.fillMaxWidth(),
                            action = { cupsOrOunces.value = !cupsOrOunces.value }
                        )
                    }

                    Column(
                        modifier = modifier
                    ) {
                        BasicExposedDropdown(
                            text = R.string.water_cups,
                            list = cups,
                            onNewValue = viewModel::onCupsChange,
                        )
                    }
                }

                Spacer(modifier = modifier.padding(10.dp))

                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = dividerColor
                )

                Column(
                    modifier = modifier.padding(10.dp)
                ) {
                    Row {
                        BasicButton(
                            text = R.string.cancel,
                            modifier = modifier.padding(horizontal = 5.dp),
                            action = { openDialog.value = false }
                        )
                        BasicButton(
                            text = R.string.submit,
                            modifier = modifier.padding(horizontal = 5.dp),
                            action = {
                                coroutineScope.launch {
                                    if (viewModel.onAddWater(cupsOrOunces.value, edit)) {
                                        openDialog.value = false
                                        viewModel.getHistoricalData()
                                        viewModel.getTodaysWater()
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

//@Preview
//@Composable
//fun AddWaterModalPreview() {
//    val openDialog = remember { mutableStateOf(true) }
//    AddWaterModal(
//        openDialog = openDialog,
//        addWater = {},
//    )
//}