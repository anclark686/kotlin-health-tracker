/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.reyaly.reyalyhealthtracker.common.composable

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun BasicField(
  @StringRes text: Int,
  value: String,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier,
  errorMsg: String? = null
) {
  val focusManager = LocalFocusManager.current

  OutlinedTextField(
    singleLine = true,
    modifier = modifier,
    value = value,
    onValueChange = { onNewValue(it) },
    label = { Text(text = stringResource(text)) },
    placeholder = { Text(stringResource(text)) },
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
    keyboardActions = KeyboardActions(
      onDone = { focusManager.moveFocus(FocusDirection.Next) }
    ),
  )
}

@Composable
fun EmailField(
  value: String,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier,
  errorMsg: String? = null
) {
  val focusManager = LocalFocusManager.current

  OutlinedTextField(
    singleLine = true,
    modifier = modifier,
    value = value,
    label = { Text(text = stringResource(R.string.email)) },
    onValueChange = { onNewValue(it) },
    placeholder = { Text(stringResource(R.string.email)) },
    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") },
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
    trailingIcon = {
      if (errorMsg != null)
        Icon(Icons.Filled.Warning,"error", tint = if (isSystemInDarkTheme()) errorPink else errorDarkRed)
    },
    keyboardActions = KeyboardActions(
      onDone = { focusManager.moveFocus(FocusDirection.Next) }
    ),
  )
}

@Composable
fun PasswordField(
  value: String,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier,
  errorMsg: String? = null
) {
  PasswordField(value, R.string.password, onNewValue, modifier, errorMsg)
}

@Composable
fun RepeatPasswordField(
  value: String,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier,
  errorMsg: String? = null
) {
  PasswordField(value, R.string.repeat_password, onNewValue, modifier, errorMsg)
}

@Composable
private fun PasswordField(
  value: String,
  @StringRes placeholder: Int,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier,
  errorMsg: String? = null
) {
  var isVisible by remember { mutableStateOf(false) }

  val icon =
    if (isVisible) painterResource(R.drawable.ic_visibility_on)
    else painterResource(R.drawable.ic_visibility_off)

  val visualTransformation =
    if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

  OutlinedTextField(
    modifier = modifier,
    value = value,
    onValueChange = { onNewValue(it) },
    label = { Text(text = stringResource(placeholder)) },
    placeholder = { Text(text = stringResource(placeholder)) },
    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
    colors = OutlinedTextFieldDefaults.colors(
      focusedContainerColor = Color.White,
      unfocusedContainerColor = Color.White,
      errorContainerColor = Color.White,
      focusedTextColor = Color.Black,
      unfocusedTextColor = Color.Black,
    ),
    trailingIcon = {
      IconButton(onClick = { isVisible = !isVisible }) {
        Icon(painter = icon, contentDescription = "Visibility")
      }
    },
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    visualTransformation = visualTransformation,
    isError = errorMsg != null,
    supportingText = {
      if (errorMsg != null) {
        Text(
          modifier = modifier,
          text = errorMsg,
          color = if (isSystemInDarkTheme()) errorPink else errorDarkRed,
        )
      }
    },
  )
}


@Composable
fun SearchField(
  @StringRes text: Int,
  value: String,
  onNewValue: (String) -> Unit,
  onSearch: () -> Unit,
  modifier: Modifier = Modifier,
  errorMsg: String? = null
) {
  val focusManager = LocalFocusManager.current

  OutlinedTextField(
    singleLine = true,
    modifier = modifier,
    value = value,
    onValueChange = { onNewValue(it) },
    label = { Text(text = stringResource(text)) },
    placeholder = { Text(stringResource(R.string.search)) },
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
    trailingIcon = {
      IconButton(onClick = { onSearch() }) {
        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
      }
    },
    keyboardActions = KeyboardActions(
      onDone = { focusManager.moveFocus(FocusDirection.Next) }
    ),
  )
}