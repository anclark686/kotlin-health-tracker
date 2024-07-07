package com.reyaly.reyalyhealthtracker.screens.breakfast.components

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.reyaly.reyalyhealthtracker.screens.breakfast.BreakfastViewModel
import com.reyaly.reyalyhealthtracker.screens.snack.SnackViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale

@Composable
fun EditBreakfastModal(
    openDialog: MutableState<Boolean>,
    editClicked: MutableState<Boolean>,
    date: MutableState<LocalDate>,
    foodItemToEdit: MutableState<FoodItem>,
    modifier: Modifier = Modifier,
    viewModel: BreakfastViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val foodState by viewModel.foodState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val dialogWidth = 300.dp
    val dialogHeight = 600.dp

    var backgroundColor: Color
    var dividerColor: Color
    var btnTextColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        dividerColor = med_sky_blue
        btnTextColor = Color.White
    } else {
        backgroundColor = light_sky_blue
        dividerColor = dark_sky_blue
        btnTextColor = Color.Black
    }

    val quantities = (1..10).map{x -> x.toString()}

    val foodName = foodItemToEdit.value.name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }

    fun closeDialog() {
        openDialog.value = false
        editClicked.value = false
        foodItemToEdit.value = FoodItem(
            documentId = "",
            meal = "",
            name = "",
            calories = "",
            protein = "",
            fat = "",
            carbs = "",
            quantity = ""
        )
        viewModel.clearFields()
    }

    suspend fun editFood() {
        viewModel.editFood(foodItemToEdit.value, date = date.value)
        closeDialog()
        viewModel.getUsersMeals(date.value)
    }

    if (
        editClicked.value &&
        foodState.name.isBlank() &&
        foodState.calories.isBlank() &&
        foodState.quantity.isBlank() &&
        foodState.carbs.isBlank() &&
        foodState.protein.isBlank() &&
        foodState.fat.isBlank()
    ) {
        viewModel.populateFieldsWithInitialValues(foodItemToEdit.value)
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { closeDialog() }) {
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
                }

                Column(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Edit $foodName", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.weight(1f))

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
                        errorMsg = uiState.quantityError,
                        initialValue = foodItemToEdit.value.quantity
                    )
                    Row() {
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
                    Row() {
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
                            text = R.string.submit,
                            modifier = modifier.fillMaxWidth(.6f),
                            action = { coroutineScope.launch { editFood() } },
                            color = btnTextColor
                        )
                    }
                }
            }
        }
    }
}