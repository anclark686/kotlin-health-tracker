package com.reyaly.reyalyhealthtracker.common.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.reflect.KSuspendFunction1

@Composable
fun FoodTable(
    foodList: MutableList<FoodItem>,
    editClicked: MutableState<Boolean>,
    foodItemToEdit: MutableState<FoodItem>,
    deleteFood: KSuspendFunction1<FoodItem, Unit>,
    modifier: Modifier = Modifier
) {
    var headerColor: Color
    var everyOtherColor: Color
    var borderColor: Color
    var deleteColor: Color
    var btnTextColor: Color
    var delBtnTextColor: Color

    if (isSystemInDarkTheme()) {
        headerColor = med_sky_blue
        everyOtherColor = Color.DarkGray
        borderColor = light_sky_blue
        deleteColor = errorPink
        btnTextColor = Color.White
        delBtnTextColor = Color.Black
    } else {
        headerColor = sky_blue
        everyOtherColor = Color.White
        borderColor = dark_sky_blue
        deleteColor = errorDarkRed
        btnTextColor = Color.Black
        delBtnTextColor = Color.White
    }

    val coroutineScope = rememberCoroutineScope()

    fun onEditClick(foodItem: FoodItem) {
        editClicked.value = true
        foodItemToEdit.value = foodItem
        Log.d("Table", "edit clicked")
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
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
                        stringResource(R.string.food_item_name),
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
                        stringResource(R.string.food_item_quantity),
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
                        stringResource(R.string.food_more),
                        modifier = modifier
                            .padding(vertical = 2.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            foodList.forEachIndexed { index, item ->
                val openMore = remember { mutableStateOf(false) }
                Row(
                    modifier = modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .background(color = if (index % 2 != 0) everyOtherColor else Color.Unspecified),
                ) {
                    Column(
                        modifier = modifier
                            .weight(.5f)
                            .border(border = BorderStroke(width = 1.dp, borderColor)),
                        horizontalAlignment = Alignment.CenterHorizontally
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
                            .border(border = BorderStroke(width = 1.dp, borderColor)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = modifier
                                .padding(vertical = 2.dp),
                            text = item.quantity,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Column(
                        modifier = modifier
                            .weight(.25f)
                            .border(border = BorderStroke(width = 1.dp, borderColor)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (!openMore.value) {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp)
                                    .clickable { openMore.value = true },
                                text = stringResource(R.string.food_macros_show),
                                textAlign = TextAlign.Center,
                            )
                        } else {
                            Text(
                                modifier = modifier
                                    .padding(vertical = 2.dp)
                                    .clickable { openMore.value = false },
                                text = stringResource(R.string.food_macros_hide),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                if (openMore.value) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        MacrosTable(foodItem = item)
                    }
                    Row() {
                        Column(
                            modifier = modifier.fillMaxWidth().padding(bottom = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp)
                            ) {
                                BasicTextButton(
                                    text = R.string.delete_food,
                                    modifier = modifier
                                        .weight(.50f)
                                        .background(color=deleteColor, RoundedCornerShape(50.dp)),
                                    action = { coroutineScope.launch {
                                        deleteFood(item)
                                    } },
                                    color = delBtnTextColor
                                )
                                Spacer(modifier = modifier.padding(10.dp))
                                BasicTextButton(
                                    text = R.string.edit_food,
                                    modifier = modifier
                                        .weight(.50f)
                                        .background(color=headerColor, RoundedCornerShape(50.dp)),
                                    action = { coroutineScope.launch {
                                        onEditClick(item)
                                    } },
                                    color = btnTextColor
                                )
                            }
                        }
                    }
                }
                
            }
        }
    }
}

//@Preview
//@Composable
//fun FoodTablePreview() {
//    val food1 = FoodItem(
//        documentId = "1234",
//        meal = "breakfast",
//        name = "eggs",
//        calories = "1234",
//        protein = "20g",
//        fat = "15g",
//        carbs = "20g",
//        apiId = "1234"
//    )
//    val food2 = FoodItem(
//        documentId = "1234",
//        meal = "breakfast",
//        name = "eggs",
//        calories = "1234",
//        protein = "20g",
//        fat = "15g",
//        carbs = "20g",
//        apiId = "1234"
//    )
//    val foods = mutableListOf<FoodItem>(food1, food2)
//    FoodTable(
//        foods
//    )
//}