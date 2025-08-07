package com.reyaly.reyalyhealthtracker.screens.med.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.helpers.capitalize
import com.reyaly.reyalyhealthtracker.model.Exercise
import com.reyaly.reyalyhealthtracker.screens.exercise.ExerciseTimeData
import com.reyaly.reyalyhealthtracker.screens.exercise.ExerciseViewModel
import com.reyaly.reyalyhealthtracker.screens.exercise.components.AddExerciseModal
import com.reyaly.reyalyhealthtracker.screens.exercise.components.MoreExerciseInfoModal
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import java.time.LocalDate
import java.util.Locale

@Composable
fun WorkoutCard(
    workoutType: String,
    exerciseTimeData: ExerciseTimeData,
    date: MutableState<LocalDate>,
    modifier: Modifier = Modifier,
    viewModel: ExerciseViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val exerciseState by viewModel.exerciseState.collectAsState()

    var labelColor: Color
    var headerColor: Color
    var everyOtherColor: Color
    var borderColor: Color
    var deleteColor: Color
    var btnTextColor: Color
    var delBtnTextColor: Color
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        labelColor = light_sky_blue
        headerColor = med_sky_blue
        everyOtherColor = Color.DarkGray
        borderColor = light_sky_blue
        deleteColor = errorPink
        btnTextColor = Color.White
        delBtnTextColor = Color.Black
        dividerColor = med_sky_blue
    } else {
        labelColor = dark_sky_blue
        headerColor = sky_blue
        everyOtherColor = Color.White
        borderColor = dark_sky_blue
        deleteColor = errorDarkRed
        btnTextColor = Color.Black
        delBtnTextColor = Color.White
        dividerColor = dark_sky_blue
    }

    val openDialog = remember { mutableStateOf(false) }
    val openMoreInfoModal = remember { mutableStateOf(false) }
    val selectedExercise = remember { mutableStateOf(Exercise()) }
    val deleteClicked = remember { mutableStateOf(false) }
    val editClicked = remember { mutableStateOf(false) }

    fun openModalAndSelectExercise(exercise: Exercise) {
        openMoreInfoModal.value = true
        selectedExercise.value = exercise
    }

    LaunchedEffect(key1 = viewModel, key2 = date.value) {
        Log.d("hello", workoutType.toString())
        viewModel.getExercises(workoutType, date.value)
        Log.d("hello", exerciseTimeData.toString())
    }

    AddExerciseModal(openDialog, workoutType, date)

    MoreExerciseInfoModal(openMoreInfoModal, selectedExercise, workoutType, deleteClicked, editClicked, date)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (workoutType == "cardio") {
                Image(
                    painter = painterResource(R.drawable.ic_cardio),
                    contentDescription = workoutType,
                    modifier = modifier.width(100.dp)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.ic_strength),
                    contentDescription = workoutType,
                    modifier = modifier.width(100.dp)
                )
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (exerciseTimeData.morning.isNotEmpty()) {
                Row(modifier = Modifier) {
                    Text(
                        stringResource(R.string.exercise_morning_workouts),
                        modifier = modifier
                            .padding(vertical = 2.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = modifier
                        .padding(start = 5.dp, top = 10.dp, end = 5.dp)
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
                            stringResource(R.string.exercise_name),
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
                            stringResource(R.string.time),
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
                            stringResource(R.string.more_info),
                            modifier = modifier
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                exerciseTimeData.morning.forEachIndexed { index, exercise ->
                    Row(
                        modifier = modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .background(color = if (index % 2 != 0) everyOtherColor else Color.Unspecified),
                    ) {
                        Column(
                            modifier = modifier
                                .weight(.5f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                text = capitalize(exercise.name),
                                textAlign = TextAlign.Center,
                            )
                        }

                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                text = "${exercise.lengthTime} mins",
                                textAlign = TextAlign.Center,
                            )
                        }

                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp)
                                    .clickable { openModalAndSelectExercise(exercise) },
                                text = stringResource(R.string.show),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.padding(5.dp))
            }

            if (exerciseTimeData.afternoon.isNotEmpty())  {
                Row(modifier = Modifier) {
                    Text(
                        stringResource(R.string.exercise_afternoon_workouts),
                        modifier = modifier
                            .padding(vertical = 2.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = modifier
                        .padding(start = 5.dp, top = 10.dp, end = 5.dp)
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
                            stringResource(R.string.exercise_name),
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
                            stringResource(R.string.time),
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
                            stringResource(R.string.more_info),
                            modifier = modifier
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                exerciseTimeData.afternoon.forEachIndexed { index, exercise ->
                    Row(
                        modifier = modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .background(color = if (index % 2 != 0) everyOtherColor else Color.Unspecified),
                    ) {
                        Column(
                            modifier = modifier
                                .weight(.5f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                text = capitalize(exercise.name),
                                textAlign = TextAlign.Center,
                            )
                        }

                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                text = "${exercise.lengthTime} mins",
                                textAlign = TextAlign.Center,
                            )
                        }

                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp)
                                    .clickable { openModalAndSelectExercise(exercise) },
                                text = stringResource(R.string.show),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.padding(5.dp))
            }

            if (exerciseTimeData.evening.isNotEmpty())  {
                Row(modifier = Modifier) {
                    Text(
                        stringResource(R.string.exercise_evening_workouts),
                        modifier = modifier
                            .padding(vertical = 2.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = modifier
                        .padding(start = 5.dp, top = 10.dp, end = 5.dp)
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
                            stringResource(R.string.exercise_name),
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
                            stringResource(R.string.time),
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
                            stringResource(R.string.more_info),
                            modifier = modifier
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                exerciseTimeData.evening.forEachIndexed { index, exercise ->
                    Row(
                        modifier = modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .background(color = if (index % 2 != 0) everyOtherColor else Color.Unspecified),
                    ) {
                        Column(
                            modifier = modifier
                                .weight(.5f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                text = capitalize(exercise.name),
                                textAlign = TextAlign.Center,
                            )
                        }

                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                text = "${exercise.lengthTime} mins",
                                textAlign = TextAlign.Center,
                            )
                        }

                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp)
                                    .clickable { openModalAndSelectExercise(exercise) },
                                text = stringResource(R.string.show),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.padding(5.dp))
            }

            if (exerciseTimeData.night.isNotEmpty())  {
                Row(modifier = Modifier) {
                    Text(
                        stringResource(R.string.exercise_night_workouts),
                        modifier = modifier
                            .padding(vertical = 2.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = modifier
                        .padding(start = 5.dp, top = 10.dp, end = 5.dp)
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
                            stringResource(R.string.exercise_name),
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
                            stringResource(R.string.time),
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
                            stringResource(R.string.more_info),
                            modifier = modifier
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                exerciseTimeData.night.forEachIndexed { index, exercise ->
                    Row(
                        modifier = modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .background(color = if (index % 2 != 0) everyOtherColor else Color.Unspecified),
                    ) {
                        Column(
                            modifier = modifier
                                .weight(.5f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                text = capitalize(exercise.name),
                                textAlign = TextAlign.Center,
                            )
                        }

                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp),
                                text = "${exercise.lengthTime} mins",
                                textAlign = TextAlign.Center,
                            )
                        }

                        Column(
                            modifier = modifier
                                .weight(.25f)
                                .fillMaxHeight()
                                .border(border = BorderStroke(width = 1.dp, borderColor)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp)
                                    .clickable { openModalAndSelectExercise(exercise) },
                                text = stringResource(R.string.show),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Spacer(modifier = modifier.padding(5.dp))
                }
            }

            if (exerciseTimeData.length <= 0) {
                Row(modifier = Modifier) {
                    Text(
                        stringResource(R.string.exercise_no_exercise),
                        modifier = modifier
                            .padding(vertical = 2.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (exerciseTimeData.length > 0) {
            Column(
                modifier = modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = dividerColor
                )

                Row(
                    modifier = modifier.fillMaxWidth().padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (workoutType == "cardio") {
                        Text(
                            stringResource(R.string.exercise_cardio_stats),
                            modifier = modifier
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                    } else {
                        Text(
                            stringResource(R.string.exercise_strength_stats),
                            modifier = modifier
                                .padding(vertical = 2.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }


                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.exercise_stats_unique),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = exerciseTimeData.length.toString(),
                        fontSize = 18.sp
                    )
                }

                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.exercise_stats_time),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = exerciseTimeData.timeSpent,
                        fontSize = 18.sp
                    )
                }

                Row (
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.exercise_stats_calories),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Text(
                        text = exerciseTimeData.calsBurned.toString(),
                        fontSize = 18.sp
                    )
                }
            }
        }

        Column(
            modifier = modifier
                .padding(top = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(
                text = R.string.exercise_add,
                modifier = modifier,
                action = { openDialog.value = true}
            )
        }
    }
}
