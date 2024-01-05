package com.reyaly.reyalyhealthtracker.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicExposedDropdown(
    @StringRes text: Int,
    list: List<String>,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMsg: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                label = { Text(text = stringResource(text)) },
                onValueChange = {},
                placeholder = { Text(stringResource(R.string.dropdown_select_one)) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                isError = errorMsg != null,
                supportingText = {
                    if (errorMsg != null) {
                        Text(
                            modifier = modifier,
                            text = errorMsg,
                            color = if (isSystemInDarkTheme()) errorPink else errorDarkRed
                        )
                    }
                },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = modifier.background(color = if (isSystemInDarkTheme()) med_sky_blue else sky_blue),
            ) {
                list.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            onNewValue(item)
                            expanded = false
                        })
                }
            }
        }
    }
}

@Preview
@Composable
fun BasicExposedDropdownPreview() {
    val sexItems = listOf(
        stringResource(R.string.intake_dropdown_sex_male),
        stringResource(R.string.intake_dropdown_sex_female),
    )
    BasicExposedDropdown(
        text = R.string.intake_input_sex,
        list = sexItems,
        onNewValue = { }
    )
}