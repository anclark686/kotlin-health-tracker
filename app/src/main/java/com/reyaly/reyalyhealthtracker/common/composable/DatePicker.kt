package com.reyaly.reyalyhealthtracker.common.composable

import android.app.DatePickerDialog
import android.content.res.Resources
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun DateField(
    @StringRes text: Int,
    value: String,
    modifier: Modifier = Modifier,
    onNewValue: (String) -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMsg: String? = null
) {

    val usFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val date = if (value.isNotBlank()) LocalDate.parse(value, usFormatter) else LocalDate.now()
    Log.d("Intake", date.toString())
    val dialog = DatePickerDialog(
        LocalContext.current,
        if (isSystemInDarkTheme()) R.style.DialogDarkTheme else R.style.DialogLightTheme,
        { _, year, month, dayOfMonth ->
            onNewValue(LocalDate.of(year, month + 1, dayOfMonth).format(usFormatter).toString())
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
    )

    var borderColor: Color

    when {
        errorMsg != null -> {
            borderColor = if (isSystemInDarkTheme()) errorPink else errorDarkRed
        }
        else -> borderColor = MaterialTheme.colorScheme.outline
    }

    OutlinedTextField(
        singleLine = true,
        value = value,
        label = { Text(text = stringResource(text)) },
        placeholder = { Text(stringResource(text)) },
        enabled = false,
        modifier = Modifier.clickable { dialog.show() },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = { onNewValue(it) },
        leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar") },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledContainerColor = Color.White,
            disabledBorderColor = borderColor,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant
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
}

@Preview
@Composable
fun DateFieldPreview() {
    DateField(
        text = R.string.intake_input_birthday,
        value = "hello"
    )
}