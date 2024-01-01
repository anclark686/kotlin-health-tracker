package com.reyaly.reyalyhealthtracker.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DashboardScreen(
    onSettingsClick: () -> Unit,
    onExerciseClick: () -> Unit,
    onFoodClick: () -> Unit,
    onMedClick: () -> Unit,
    onWaterClick: () -> Unit,
    onWeightClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyy")
    var backgroundColor: Color
    var shadowColor: Color
    var colorList: List<Color>
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        colorList = listOf<Color>(dark_sky_blue, med_sky_blue, dark_sky_blue)
        shadowColor = Color.LightGray
        dividerColor = med_sky_blue
    } else {
        backgroundColor = light_sky_blue
        colorList = listOf<Color>(med_sky_blue, sky_blue, med_sky_blue)
        shadowColor = Color.Black
        dividerColor = dark_sky_blue
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoBanner()

        Column(
            modifier = modifier
                .background(color = backgroundColor)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column (
                modifier = modifier
                    .padding(start = 10.dp, top = 50.dp, end = 10.dp, bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Welcome Alycia!",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Divider(
                modifier = modifier
                    .fillMaxWidth(),
                thickness = 2.dp,
                color = dividerColor
            )
        }

        Column(
            modifier = modifier
                .padding(40.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp),
                    spotColor = shadowColor
                )
                .background(color = backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .background(Brush.horizontalGradient(colorList))
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.dashboard_stats),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            Column(
                modifier = modifier
                    .padding(10.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_date),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = LocalDate.now().format(formatter),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_streak),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "**TODO**",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_max_cal),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "**TODO**",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_cal_in),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "**TODO**",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_cal_out),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "**TODO**",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_curr_weight),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "**TODO**",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_goal_weight),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "**TODO**",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }


        }

        Column(modifier = modifier
            .padding(40.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp),
                spotColor = shadowColor
            )
            .background(color = backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .background(Brush.horizontalGradient(colorList))
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.text_trackers),
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = modifier.fillMaxSize().padding(10.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(

                ) {
                    Button(
                        modifier = Modifier.width(150.dp).padding(5.dp),
                        onClick = { onWeightClick() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = med_sky_blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(R.string.text_weight), fontSize = 20.sp)
                    }
                    Button(
                        modifier = Modifier.width(150.dp).padding(5.dp),
                        onClick = { onExerciseClick() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = med_sky_blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(R.string.text_exercise), fontSize = 20.sp)
                    }
                }

                Row(

                ) {
                    Button(
                        modifier = Modifier.width(150.dp).padding(5.dp),
                        onClick = { onFoodClick() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = med_sky_blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(R.string.text_food), fontSize = 20.sp)
                    }
                    Button(
                        modifier = Modifier.width(150.dp).padding(5.dp),
                        onClick = { onWaterClick() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = med_sky_blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(R.string.text_water), fontSize = 20.sp)
                    }
                }


                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        modifier = Modifier.width(170.dp).padding(5.dp),
                        onClick = { onMedClick() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = med_sky_blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(stringResource(R.string.text_med), fontSize = 20.sp)
                    }
                }
        }


        }
    }
}

@Preview
@Composable
fun DashboardPreview() {
    DashboardScreen(
        onSettingsClick = { },
        onExerciseClick = { },
        onFoodClick = { },
        onMedClick = { },
        onWaterClick = { },
        onWeightClick = { },
        modifier = Modifier
    )
}