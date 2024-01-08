package com.reyaly.reyalyhealthtracker.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue


@Composable
fun MacrosTable(
    foodItem: FoodItem,
    modifier: Modifier = Modifier
) {
    var labelColor: Color
    var spinnerColor: Color
    var headerColor: Color
    var everyOtherColor: Color
    var borderColor: Color

    if (isSystemInDarkTheme()) {
        labelColor = light_sky_blue
        headerColor = med_sky_blue
        everyOtherColor = Color.DarkGray
        borderColor = light_sky_blue
        spinnerColor = sky_blue
    } else {
        labelColor = dark_sky_blue
        headerColor = sky_blue
        everyOtherColor = Color.White
        borderColor = dark_sky_blue
        spinnerColor = dark_sky_blue
    }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                .fillMaxWidth()
                .background(color = headerColor)

        ) {
            Column(
                modifier = modifier
                    .weight(.33f)
                    .border(border = BorderStroke(width = 1.dp, borderColor)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.food_item_protein),
                    modifier = modifier
                        .padding(vertical = 2.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = modifier
                    .weight(.33f)
                    .border(border = BorderStroke(width = 1.dp, borderColor)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.food_item_fat),
                    modifier = modifier
                        .padding(vertical = 2.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = modifier
                    .weight(.33f)
                    .border(border = BorderStroke(width = 1.dp, borderColor)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.food_item_carbs),
                    modifier = modifier
                        .padding(vertical = 2.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = modifier
                .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = modifier
                    .weight(.33f)
                    .border(border = BorderStroke(width = 1.dp, borderColor)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier
                        .padding(vertical = 2.dp),
                    text = foodItem.protein,
                    textAlign = TextAlign.Center,
                )
            }

            Column(
                modifier = modifier
                    .weight(.33f)
                    .border(border = BorderStroke(width = 1.dp, borderColor)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier
                        .padding(vertical = 2.dp),
                    text = foodItem.fat,
                    textAlign = TextAlign.Center,
                )
            }
            Column(
                modifier = modifier
                    .weight(.33f)
                    .border(border = BorderStroke(width = 1.dp, borderColor)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier
                        .padding(vertical = 2.dp),
                    text = foodItem.carbs,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
fun MacrosTablePreview() {
    val food = FoodItem(
        documentId = "1234",
        meal = "breakfast",
        name = "eggs",
        brand = "generic",
        calories = "1234",
        protein = "20g",
        fat = "15g",
        carbs = "20g",
        apiId = 1234
    )
    MacrosTable(
        food
    )
}