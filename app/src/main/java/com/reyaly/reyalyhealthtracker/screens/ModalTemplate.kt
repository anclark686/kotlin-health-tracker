package com.reyaly.reyalyhealthtracker.screens

import androidx.annotation.StringRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue

@Composable
fun ModalTemplate(
    openDialog: MutableState<Boolean>,
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dialogWidth = 300.dp
    val dialogHeight = 300.dp

    var backgroundColor: Color
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        dividerColor = med_sky_blue
    } else {
        backgroundColor = light_sky_blue
        dividerColor = dark_sky_blue
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            Column(
                modifier
                    .size(dialogWidth, dialogHeight)
                    .background(backgroundColor, RoundedCornerShape(8.dp)),
//                verticalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 25.dp, end = 20.dp, bottom = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Warning",
                        modifier = Modifier.size(30.dp)
                    )

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
                        modifier = modifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        BasicTextButton(
                            text = R.string.delete_dismiss,
                            modifier = modifier.fillMaxWidth(.4f),
                            action = { openDialog.value = false }
                        )
                        BasicTextButton(
                            text = R.string.delete_confirm,
                            modifier = modifier.fillMaxWidth(.6f),
                            action = { onAction() }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ModalTemplatePreview() {
    val openDialog = remember { mutableStateOf(true) }
    ModalTemplate(
        openDialog = openDialog,
        onAction = {},
        modifier = Modifier,
    )
}