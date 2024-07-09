package com.reyaly.reyalyhealthtracker.screens.weight.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.screens.water.components.AddWaterModal
import com.reyaly.reyalyhealthtracker.screens.weight.WeightViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import kotlinx.coroutines.launch

@Composable
fun AddWeightModal(
    openDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    viewModel: WeightViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val weightState by viewModel.weightState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val dialogWidth = 300.dp
    val dialogHeight = 250.dp

    var dialogColor: Color

    if (isSystemInDarkTheme()) {
        dialogColor = dark_sky_blue
    } else {
        dialogColor = light_sky_blue
    }

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
                    modifier = modifier.padding(10.dp)
                ) {
                    Text(
                        stringResource(R.string.weight_enter),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Column(
                    modifier = modifier
                ) {
                    BasicField(
                        text = R.string.weight_new,
                        value = weightState.weight,
                        onNewValue = viewModel::onWeightChange,
                        modifier = modifier.padding(horizontal = 15.dp)
                    )
                }
                Column(
                    modifier = modifier.padding(10.dp)
                ) {
                    Row() {
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
                                    openDialog.value = false
                                    viewModel.onAddNewWeight()
                                    viewModel.getWeightGoals()
                                    viewModel.getWeightStats()
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
//fun AddWeightModalPreview() {
//    val openDialog = remember { mutableStateOf(true) }
//    AddWeightModal(
//        openDialog = openDialog,
//        addWeight = {},
//    )
//}