package com.reyaly.reyalyhealthtracker.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicExposedDropdown
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.common.composable.SearchField
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue

@Composable
fun AddFoodModal(
    meal: String,
    openDialog: MutableState<Boolean>,
    onAdd: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val dialogWidth = 300.dp
    val dialogHeight = 600.dp

    var backgroundColor: Color
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        dividerColor = med_sky_blue
    } else {
        backgroundColor = light_sky_blue
        dividerColor = dark_sky_blue
    }

    val openManual = remember { mutableStateOf(false) }

    val quantities = (1..10).map{x -> x.toString()}

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
                }

                if (!openManual.value) {
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BasicTextButton(
                            text = R.string.manual,
                            modifier = modifier.fillMaxWidth(),
                            action = { openManual.value = true }
                        )
                    }

                    Column(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SearchField(
                            text = R.string.search_switch,
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
                        Text(
                            text = stringResource(R.string.results),
                            fontSize = 18.sp
                        )
                        Text(text = "Results... Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                        Text(text = "Results... Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                        Text(text = "Results... Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
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
                            action = { openManual.value = false }
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
                            value = "hello",
                            onNewValue = {}
                        )
                        BasicExposedDropdown(
                            text = R.string.food_item_quantity,
                            list = quantities,
                            onNewValue = {}
                        )
                        Row() {
                            BasicField(
                                modifier = modifier
                                    .weight(.5f)
                                    .padding(horizontal = 10.dp),
                                text = R.string.food_item_calories,
                                value = "hello",
                                onNewValue = {}
                            )
                            BasicField(
                                modifier = modifier
                                    .weight(.5f)
                                    .padding(horizontal = 10.dp),
                                text = R.string.food_item_protein,
                                value = "hello",
                                onNewValue = {}
                            )
                        }
                        Row() {
                            BasicField(
                                modifier = modifier
                                    .weight(.5f)
                                    .padding(horizontal = 10.dp),
                                text = R.string.food_item_fat,
                                value = "hello",
                                onNewValue = {}
                            )
                            BasicField(
                                modifier = modifier
                                    .weight(.5f)
                                    .padding(horizontal = 10.dp),
                                text = R.string.food_item_carbs,
                                value = "hello",
                                onNewValue = {}
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Column(modifier = modifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    Divider(
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
                            action = { openDialog.value = false }
                        )
                        BasicTextButton(
                            text = R.string.add,
                            modifier = modifier.fillMaxWidth(.6f),
                            action = { onAdd(meal) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddFoodModalPreview() {
    val openDialog = remember { mutableStateOf(true) }
    AddFoodModal(
        "breakfast",
        openDialog = openDialog,
        onAdd = {}
    )
}