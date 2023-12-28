package com.reyaly.reyalyhealthtracker.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue

@Composable
fun Descriptions(modifier: Modifier = Modifier) {
    var backgroundColor: Color
    var shadowColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        shadowColor = Color.LightGray
    } else {
        backgroundColor = light_sky_blue
        shadowColor = Color.Black
    }

    Column(
        modifier = modifier
            .padding(15.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp),
                spotColor = shadowColor
            )
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(5.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            Column(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(R.drawable.weight),
                    contentDescription = "scale",
                    modifier = modifier
                        .width(100.dp)
                        .padding(15.dp)
                )
            }

            Row(
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.home_weight),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

        }

        Column(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(5.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            Column(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(R.drawable.food),
                    contentDescription = "Logo",
                    modifier = modifier
                        .width(100.dp)
                        .padding(15.dp)
                )
            }

            Row(
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.home_food),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

        }

        Column(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(5.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            Column(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(R.drawable.water),
                    contentDescription = "Logo",
                    modifier = modifier
                        .width(100.dp)
                        .padding(15.dp)
                )
            }

            Row(
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.home_water),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

        }

        Column(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(5.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            Column(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(R.drawable.exercise),
                    contentDescription = "Logo",
                    modifier = modifier
                        .width(100.dp)
                        .padding(15.dp)
                )
            }

            Row(
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.home_exercise),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

        }

        Column(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(5.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            Column(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(R.drawable.meds),
                    contentDescription = "Logo",
                    modifier = modifier
                        .width(100.dp)
                        .padding(15.dp)

                )
            }

            Row(
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.home_med),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

@Preview
@Composable
fun DescriptionsPreview() {
    Descriptions(
        modifier = Modifier.fillMaxSize()
    )
}