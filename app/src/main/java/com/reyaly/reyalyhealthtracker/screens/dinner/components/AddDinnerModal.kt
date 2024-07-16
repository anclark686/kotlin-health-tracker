package com.reyaly.reyalyhealthtracker.screens.dinner.components

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
import com.reyaly.reyalyhealthtracker.common.composable.BasicExposedDropdown
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.common.composable.SearchField
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.screens.dinner.DinnerViewModel
import com.reyaly.reyalyhealthtracker.screens.food.FoodItems
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun AddDinnerModal(
    openDialog: MutableState<Boolean>,
    date: MutableState<LocalDate>,
    modifier: Modifier = Modifier,
    viewModel: DinnerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val foodState by viewModel.foodState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val dialogWidth = 300.dp
    val dialogHeight = 650.dp

    var backgroundColor: Color
    var dividerColor: Color
    var headerList: List<Color>
    var subSectionColor: Color
    var highlightColor: Color
    var btnTextColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        dividerColor = med_sky_blue
        headerList = listOf<Color>(med_sky_blue, dark_sky_blue)
        subSectionColor = Color.Black
        highlightColor = med_sky_blue
        btnTextColor = Color.White
    } else {
        backgroundColor = light_sky_blue
        dividerColor = dark_sky_blue
        headerList = listOf<Color>(sky_blue, med_sky_blue)
        subSectionColor = Color.White
        highlightColor = sky_blue
        btnTextColor = Color.Black
    }

    val openManual = remember { mutableStateOf(true) }
    val openBreakfast = remember { mutableStateOf(false) }
    val openLunch = remember { mutableStateOf(false) }
    val openDinner = remember { mutableStateOf(false) }
    val openSnacks = remember { mutableStateOf(false) }

    val quantities = (1..10).map{x -> x.toString()}

    val foodItems: MutableState<FoodItems?> = remember { mutableStateOf(FoodItems(
        breakfast = mutableListOf(),
        lunch = mutableListOf(),
        dinner = mutableListOf(),
        snacks = mutableListOf()
    )) }

    val selectedFood: MutableState<FoodItem?> = remember { mutableStateOf(null) }

    fun closeDialog() {
        openDialog.value = false
        openManual.value = true
    }

    suspend fun addFood() {
        when {
            openManual.value -> {
                viewModel.addOrEditFoodManual(date = date.value)
            }
            selectedFood.value != null && !openManual.value -> {
                viewModel.onAddEditFoodInDates(selectedFood.value!!, date.value)
                selectedFood.value = null
            }
        }
        closeDialog()
        viewModel.getUsersMeals(date.value)
    }

    LaunchedEffect(key1 = openDialog.value) {
        if (openDialog.value) {
            foodItems.value = viewModel.getAllFoods()
            Log.d("hello", "you reloading?")
        }
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            Column(
                modifier
                    .size(dialogWidth, dialogHeight)
                    .background(backgroundColor, RoundedCornerShape(8.dp)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_food),
                        contentDescription = "food",
                        modifier = modifier.width(40.dp)
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    Text(
                        stringResource(R.string.add_food),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth(),
                    thickness = 2.dp,
                    color = dividerColor
                )

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

                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(10.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(headerList),
                                        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                                    ),
                            ) {
                                BasicTextButton(
                                    text = R.string.breakfast,
                                    modifier = modifier.fillMaxWidth(),
                                    action = { openBreakfast.value = !openBreakfast.value },
                                    color = btnTextColor
                                )
                            }
                            if (openBreakfast.value && foodItems.value?.breakfast?.isNotEmpty() == true) {
                                Column(
                                    modifier = modifier
                                        .background(color = subSectionColor,
                                            RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    foodItems.value?.breakfast?.forEach { food ->
                                        if (selectedFood.value == food) {
                                            TextButton(
                                                onClick = { selectedFood.value = null },
                                                modifier = modifier.background(color = highlightColor).fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = food.name,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 18.sp,
                                                    color = btnTextColor
                                                )
                                            }
                                        } else {
                                            TextButton(onClick = { selectedFood.value = food }, modifier = modifier.fillMaxWidth()) {
                                                Text(
                                                    text = food.name,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 18.sp,
                                                    color = btnTextColor
                                                )
                                            }
                                        }
                                    }
                                }
                            } else if (openBreakfast.value) {
                                Column(
                                    modifier = modifier
                                        .background(color = subSectionColor,
                                            RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(text = stringResource(R.string.food_no_foods), textAlign = TextAlign.Center)
                                }
                            }
                        }
                        Spacer(modifier = modifier.padding(5.dp))
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(headerList),
                                        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                                    ),
                            ) {
                                BasicTextButton(
                                    text = R.string.lunch,
                                    modifier = modifier.fillMaxWidth(),
                                    action = { openLunch.value = !openLunch.value },
                                    color = btnTextColor
                                )
                            }
                            if (openLunch.value && foodItems.value?.lunch?.isNotEmpty() == true) {
                                Column(
                                    modifier = modifier
                                        .background(color = subSectionColor,
                                            RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    foodItems.value?.lunch?.forEach { food ->
                                        if (selectedFood.value == food) {
                                            TextButton(
                                                onClick = { selectedFood.value = null },
                                                modifier = modifier.background(color = highlightColor).fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = food.name,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 18.sp,
                                                    color = btnTextColor
                                                )
                                            }
                                        } else {
                                            TextButton(onClick = { selectedFood.value = food }, modifier = modifier.fillMaxWidth()) {
                                                Text(
                                                    text = food.name,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 18.sp,
                                                    color = btnTextColor
                                                )
                                            }
                                        }
                                    }
                                }
                            } else if (openLunch.value) {
                                Column(
                                    modifier = modifier
                                        .background(color = subSectionColor,
                                            RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(text = stringResource(R.string.food_no_foods), textAlign = TextAlign.Center)
                                }
                            }
                        }
                        Spacer(modifier = modifier.padding(5.dp))
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(headerList),
                                        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                                    ),
                            ) {
                                BasicTextButton(
                                    text = R.string.dinner,
                                    modifier = modifier.fillMaxWidth(),
                                    action = { openDinner.value = !openDinner.value },
                                    color = btnTextColor
                                )
                            }
                            if (openDinner.value && foodItems.value?.dinner?.isNotEmpty() == true) {
                                Column(
                                    modifier = modifier
                                        .background(color = subSectionColor,
                                            RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    foodItems.value?.dinner?.forEach { food ->
                                        if (selectedFood.value == food) {
                                            TextButton(
                                                onClick = { selectedFood.value = null },
                                                modifier = modifier.background(color = highlightColor).fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = food.name,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 18.sp,
                                                    color = btnTextColor
                                                )
                                            }
                                        } else {
                                            TextButton(onClick = { selectedFood.value = food }, modifier = modifier.fillMaxWidth()) {
                                                Text(
                                                    text = food.name,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 18.sp,
                                                    color = btnTextColor
                                                )
                                            }
                                        }
                                    }
                                }
                            } else if (openDinner.value) {
                                Column(
                                    modifier = modifier
                                        .background(color = subSectionColor,
                                            RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(text = stringResource(R.string.food_no_foods), textAlign = TextAlign.Center)
                                }
                            }
                        }
                        Spacer(modifier = modifier.padding(5.dp))
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(headerList),
                                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
                                )
                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                            ) {
                                BasicTextButton(
                                    text = R.string.snacks,
                                    modifier = modifier.fillMaxWidth(),
                                    action = { openSnacks.value = !openSnacks.value },
                                    color = btnTextColor
                                )
                            }
                            if (openSnacks.value && foodItems.value?.snacks?.isNotEmpty() == true) {
                                Column(
                                    modifier = modifier
                                        .background(color = subSectionColor,
                                            RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    foodItems.value?.snacks?.forEach { food ->
                                        if (selectedFood.value == food) {
                                            TextButton(
                                                onClick = { selectedFood.value = null },
                                                modifier = modifier.background(color = highlightColor).fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = food.name,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 18.sp,
                                                    color = btnTextColor
                                                )
                                            }
                                        } else {
                                            TextButton(onClick = { selectedFood.value = food }, modifier = modifier.fillMaxWidth()) {
                                                Text(
                                                    text = food.name,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 18.sp,
                                                    color = btnTextColor
                                                )
                                            }
                                        }
                                    }
                                }
                            } else if (openSnacks.value) {
                                Column(
                                    modifier = modifier
                                        .background(color = subSectionColor,
                                            RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(text = stringResource(R.string.food_no_foods), textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                } else {
                    // Show the manual section
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
                    Spacer(modifier = modifier.padding(20.dp))
                    Column(
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BasicField(
                            text = R.string.food_item_name,
                            value = foodState.name,
                            onNewValue = viewModel::onNameChange,
                            errorMsg = uiState.nameError
                        )
                        BasicExposedDropdown(
                            text = R.string.food_item_quantity,
                            list = quantities,
                            onNewValue = viewModel::onQuantityChange,
                            errorMsg = uiState.quantityError
                        )
                        Row {
                            BasicField(
                                modifier = modifier
                                    .weight(.5f)
                                    .padding(horizontal = 10.dp),
                                text = R.string.food_item_calories,
                                value = foodState.calories,
                                onNewValue = viewModel::onCaloriesChange,
                                errorMsg = uiState.caloriesError
                            )
                            BasicField(
                                modifier = modifier
                                    .weight(.5f)
                                    .padding(horizontal = 10.dp),
                                text = R.string.food_item_protein,
                                value = foodState.protein,
                                onNewValue = viewModel::onProteinChange,
                                errorMsg = uiState.proteinError
                            )
                        }
                        Row {
                            BasicField(
                                modifier = modifier
                                    .weight(.5f)
                                    .padding(horizontal = 10.dp),
                                text = R.string.food_item_fat,
                                value = foodState.fat,
                                onNewValue = viewModel::onFatChange,
                                errorMsg = uiState.fatError
                            )
                            BasicField(
                                modifier = modifier
                                    .weight(.5f)
                                    .padding(horizontal = 10.dp),
                                text = R.string.food_item_carbs,
                                value = foodState.carbs,
                                onNewValue = viewModel::onCarbsChange,
                                errorMsg = uiState.carbsError
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Column(modifier = modifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    HorizontalDivider(
                        modifier = modifier
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = dividerColor
                    )
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        BasicTextButton(
                            text = R.string.delete_dismiss,
                            modifier = modifier.fillMaxWidth(.4f),
                            action = { closeDialog() },
                            color = btnTextColor
                        )
                        BasicTextButton(
                            text = R.string.add,
                            modifier = modifier.fillMaxWidth(.6f),
                            action = { coroutineScope.launch { addFood() } },
                            color = btnTextColor
                        )
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun AddFoodModalPreview() {
//    val openDialog = remember { mutableStateOf(true) }
//    AddDinnerModal(
//        "breakfast",
//        openDialog = openDialog,
//        onAdd = {}
//    )
//}