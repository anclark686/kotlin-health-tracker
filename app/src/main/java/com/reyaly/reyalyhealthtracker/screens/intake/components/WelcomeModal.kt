package com.reyaly.reyalyhealthtracker.screens.intake.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.DeleteModal
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun WelcomeModal(
    openDialog: MutableState<Boolean>,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dialogWidth = 300.dp
    val dialogHeight = 400.dp

    var backgroundColor: Color
    var dividerColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = med_sky_blue
        dividerColor = dark_sky_blue
    } else {
        backgroundColor = sky_blue
        dividerColor = dark_sky_blue
    }

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            // Draw a rectangle shape with rounded corners inside the dialog
            Column(
                modifier
                    .size(dialogWidth, dialogHeight)
                    .background(backgroundColor, RoundedCornerShape(8.dp)),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Column(modifier = modifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column (
                        modifier = modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.rht_logo_no_background),
                            contentDescription = "logo",
                            modifier = modifier
                                .width(75.dp)
                                .padding(15.dp),
                        )
                        Text(
                            stringResource(R.string.intake_welcome_header),
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = modifier.padding(5.dp))
                        Text(
                            stringResource(R.string.intake_welcome_first),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = modifier.padding(5.dp))
                        Text(
                            stringResource(R.string.intake_welcome_info),
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = modifier.padding(5.dp))
                        Text(
                            stringResource(R.string.intake_welcome_continue),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                    }

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
                            text = R.string.nav_logout,
                            modifier = modifier.fillMaxWidth(.4f),
                            action = { onLogout() }
                        )
                        BasicTextButton(
                            text = R.string.cont,
                            modifier = modifier.fillMaxWidth(.6f),
                            action = { openDialog.value = false }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WelcomeModalPreview() {
    val openDialog = remember { mutableStateOf(true) }
    WelcomeModal(
        openDialog = openDialog,
        onLogout = {},
        modifier = Modifier,
    )
}