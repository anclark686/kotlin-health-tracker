package com.reyaly.reyalyhealthtracker.screens.breakfast

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.AddFoodModal
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.common.components.FoodTable
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.DashboardButton
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.screens.breakfast.components.BreakfastStats

@Composable
fun BreakfastScreen(
    onDashboardClick: () -> Unit,
    onFoodClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    val food1 = FoodItem(
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
    val food2 = FoodItem(
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

    val food3 = FoodItem(
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

    val foods = listOf<FoodItem>(food1, food2, food3)

    val openDialog = remember { mutableStateOf(false) }

    AddFoodModal(
        openDialog = openDialog,
        onAdd = {}
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
//        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = modifier) {
            LogoBanner()
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                BasicButton(text = R.string.text_dashboard, modifier = modifier, action = { onDashboardClick() })
                Spacer(modifier = modifier.padding(5.dp))
                BasicButton(text = R.string.nav_food, modifier = modifier, action = { onFoodClick() })
            }
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.padding(20.dp))
            Image(
                painter = painterResource(R.drawable.ic_breakfast),
                contentDescription = "breakfast",
                modifier = modifier
                    .width(150.dp)
                    .padding(horizontal = 5.dp)
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContentSection(
                contentComposable = { FoodTable(foods) },
                text = R.string.breakfast_items
            )
            BasicButton(
                text = R.string.add_food,
                modifier = modifier.padding(10.dp),
                action = { openDialog.value = true }
            )
        }

        ContentSection(
            contentComposable = { BreakfastStats() },
            text = R.string.breakfast_stats
        )

        Spacer(modifier = modifier.padding(20.dp))
    }
}

@Preview
@Composable
fun BreakfastScreenPreview() {
    BreakfastScreen(
        onDashboardClick = {},
        onFoodClick = {}
    )
}