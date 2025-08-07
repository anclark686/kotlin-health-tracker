package com.reyaly.reyalyhealthtracker.screens.exercise.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicExposedDropdown
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.common.composable.SearchField
import com.reyaly.reyalyhealthtracker.helpers.capitalize
import com.reyaly.reyalyhealthtracker.model.Exercise
import com.reyaly.reyalyhealthtracker.screens.exercise.ExerciseViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale

@Composable
fun AddExerciseModal(
    openDialog: MutableState<Boolean>,
    workoutType: String,
    date: MutableState<LocalDate>,
    modifier: Modifier = Modifier,
    edit: Boolean = false,
    viewModel: ExerciseViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val exerciseState by viewModel.exerciseState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val dialogWidth = 300.dp
    val dialogHeight = 550.dp

    var dialogColor: Color
    var dividerColor: Color
    var backgroundColor: Color
    var headerList: List<Color>
    var subSectionColor: Color
    var highlightColor: Color
    var btnTextColor: Color

    if (isSystemInDarkTheme()) {
        dialogColor = dark_sky_blue
        dividerColor = med_sky_blue
        backgroundColor = dark_sky_blue
        headerList = listOf<Color>(med_sky_blue, dark_sky_blue)
        subSectionColor = Color.Black
        highlightColor = med_sky_blue
        btnTextColor = Color.White
    } else {
        dialogColor = light_sky_blue
        dividerColor = dark_sky_blue
        backgroundColor = light_sky_blue
        headerList = listOf<Color>(sky_blue, med_sky_blue)
        subSectionColor = Color.White
        highlightColor = sky_blue
        btnTextColor = Color.Black
    }

    val intensityLevels = listOf(
        stringResource(R.string.exercise_intensity_passive),
        stringResource(R.string.exercise_intensity_light),
        stringResource(R.string.exercise_intensity_moderate),
        stringResource(R.string.exercise_intensity_vigorous),
        stringResource(R.string.exercise_intensity_extreme),
    )

    val timeOfDay = listOf(
        stringResource(R.string.exercise_morning),
        stringResource(R.string.exercise_afternoon),
        stringResource(R.string.exercise_evening),
        stringResource(R.string.exercise_night),
    )

    val openManual = remember { mutableStateOf(true) }
    val selectedExercise: MutableState<Exercise?> = remember { mutableStateOf(null) }

    fun closeDialog() {
        openDialog.value = false
        openManual.value = true
    }



    suspend fun addExercise() {
        when {
            openManual.value -> {
                if (viewModel.onAddNewExercise(workoutType.lowercase(), date.value)) {
                    closeDialog()
                }
            }
            selectedExercise.value != null && !openManual.value -> {
                if (viewModel.addExistingExercise(selectedExercise.value!!, date.value)) {
                    closeDialog()
                    viewModel.getExercises(workoutType.lowercase(), date.value)
                }
            }
        }
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { closeDialog() }) {
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
                        painter = painterResource(R.drawable.ic_exercise),
                        contentDescription = "meds",
                        modifier = modifier
                            .width(75.dp)
                            .padding(15.dp),
                    )
                    if (!edit) {
                        val capWorkoutType = capitalize(workoutType)
                        Text(
                            text = "Add New $capWorkoutType Exercise",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                        )
                    } else {
//                        Text(
//                            "${stringResource(R.string.weight_enter_historical)} ${uiState.historicalDate}:",
//                            style = MaterialTheme.typography.headlineSmall
//                        )
                    }
                }
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = dividerColor
                )

                Spacer(modifier = modifier.padding(10.dp))

                if (!openManual.value) {
                    // Show the search section
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BasicTextButton(
                            text = R.string.manual,
                            modifier = modifier.fillMaxWidth(),
                            action = { openManual.value = true },
                            color = btnTextColor
                        )
                    }

                    Spacer(modifier = modifier.padding(10.dp))

                    Column(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SearchField(
                            text = R.string.search,
                            value = "Hello",
                            onNewValue = {},
                            onSearch = {},
                            errorMsg = null
                        )
                    }

                    if ( workoutType == "cardio" && uiState.cardioList.isNotEmpty()) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth(.90f)
                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(headerList),
                                        RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 0.dp
                                        )
                                    ),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.exercise_previous),
                                    modifier = modifier.fillMaxWidth().padding(10.dp),
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Column(
                            modifier = modifier
                                .background(
                                    color = subSectionColor,
                                    RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                )
                                .fillMaxWidth(.90f)
                                .padding(15.dp)
                                .height(300.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            uiState.cardioList.forEachIndexed { index, exercise ->
                                if (selectedExercise.value == exercise) {
                                    TextButton(
                                        onClick = { selectedExercise.value = null },
                                        modifier = modifier
                                            .background(color = highlightColor)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = capitalize(exercise.name),
                                            textAlign = TextAlign.Center,
                                            fontSize = 18.sp,
                                            color = btnTextColor
                                        )
                                    }
                                } else {
                                    TextButton(onClick = { selectedExercise.value = exercise }, modifier = modifier.fillMaxWidth()) {
                                        Text(
                                            text = capitalize(exercise.name),
                                            textAlign = TextAlign.Center,
                                            fontSize = 18.sp,
                                            color = btnTextColor
                                        )
                                    }
                                }

                                if (index != uiState.cardioList.count() - 1 ) {
                                    HorizontalDivider(
                                        modifier = modifier
                                            .fillMaxWidth(),
                                        thickness = 2.dp,
                                        color = dividerColor
                                    )
                                }
                            }
                        }
                    } else if (workoutType == "strength" && uiState.strengthList.isNotEmpty()) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth(.90f)
                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(headerList),
                                        RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 0.dp
                                        )
                                    ),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.exercise_previous),
                                    modifier = modifier.fillMaxWidth().padding(10.dp),
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Column(
                            modifier = modifier
                                .background(
                                    color = subSectionColor,
                                    RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                )
                                .fillMaxWidth(.90f)
                                .padding(15.dp)
                                .height(300.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            uiState.strengthList.forEachIndexed { index, exercise ->
                                if (selectedExercise.value == exercise) {
                                    TextButton(
                                        onClick = { selectedExercise.value = null },
                                        modifier = modifier
                                            .background(color = highlightColor)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = capitalize(exercise.name),
                                            textAlign = TextAlign.Center,
                                            fontSize = 18.sp,
                                            color = btnTextColor
                                        )
                                    }
                                } else {
                                    TextButton(onClick = { selectedExercise.value = exercise }, modifier = modifier.fillMaxWidth()) {
                                        Text(
                                            text = capitalize(exercise.name),
                                            textAlign = TextAlign.Center,
                                            fontSize = 18.sp,
                                            color = btnTextColor
                                        )
                                    }
                                }

                                if (index != uiState.strengthList.count() - 1 ) {
                                    HorizontalDivider(
                                        modifier = modifier
                                            .fillMaxWidth(),
                                        thickness = 2.dp,
                                        color = dividerColor
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BasicTextButton(
                            text = R.string.search,
                            modifier = modifier.fillMaxWidth(.4f),
                            action = { openManual.value = false },
                            color = btnTextColor
                        )
                    }

                    Spacer(modifier = modifier.padding(10.dp))

                    Column(
                        modifier = modifier
                    ) {
                        BasicField(
                            text = R.string.exercise_name,
                            value = exerciseState.name,
                            onNewValue = viewModel::onNameChange,
                            modifier = modifier.padding(horizontal = 10.dp),
                            errorMsg = uiState.nameError
                        )

                        BasicField(
                            text = R.string.exercise_cals,
                            value = exerciseState.calsBurned,
                            onNewValue = viewModel::onCalsBurnedChange,
                            modifier = modifier.padding(horizontal = 10.dp),
                            errorMsg = uiState.calsBurnedError
                        )

                        BasicField(
                            text = R.string.exercise_length,
                            value = exerciseState.lengthTime,
                            onNewValue = viewModel::onLengthTimeChange,
                            modifier = modifier.padding(horizontal = 10.dp),
                            errorMsg = uiState.lengthTimeError
                        )

                        BasicExposedDropdown(
                            text = R.string.exercise_time,
                            list = timeOfDay,
                            onNewValue = viewModel::onTimeOfDayChange,
                            errorMsg = uiState.timeOfDayError,
                        )

                        if (workoutType == "Strength") {
                            BasicField(
                                text = R.string.exercise_area,
                                value = exerciseState.area,
                                onNewValue = viewModel::onAreaChange,
                                modifier = modifier.padding(horizontal = 10.dp),
                                errorMsg = uiState.areaError
                            )
                        }

                        BasicExposedDropdown(
                            text = R.string.exercise_intensity,
                            list = intensityLevels,
                            onNewValue = viewModel::onIntensityChange,
                            errorMsg = uiState.intensityError,
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
                            action = { closeDialog() }
                        )
                        BasicButton(
                            text = R.string.submit,
                            modifier = modifier.padding(horizontal = 5.dp),
                            action = {
                                coroutineScope.launch {
                                    addExercise()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}