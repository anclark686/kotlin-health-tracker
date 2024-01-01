package com.reyaly.reyalyhealthtracker.screens.changepw

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.common.composable.DashboardButton
import com.reyaly.reyalyhealthtracker.common.composable.PasswordField
import com.reyaly.reyalyhealthtracker.common.composable.RepeatPasswordField
import com.reyaly.reyalyhealthtracker.screens.signup.SignUpViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue

@Composable
fun ChangePwScreen(
    onSettingsRedirect: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChangePwViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    var backgroundColor: Color
    var deleteColor: Color
    var dividerColor: Color
    var shadowColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        deleteColor = errorPink
        dividerColor = med_sky_blue
        shadowColor = Color.LightGray
    } else {
        backgroundColor = light_sky_blue
        deleteColor = errorDarkRed
        dividerColor = dark_sky_blue
        shadowColor = Color.Black
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(modifier = modifier) {
            LogoBanner()
        }

        Column(
            modifier = modifier
                .padding(40.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp),
                    spotColor = shadowColor
                )
                .background(color = backgroundColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = modifier.padding(
                    start = 20.dp,
                    top = 25.dp,
                    end = 20.dp,
                    bottom = 30.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Account",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = modifier.padding(5.dp))
                Text(
                    stringResource(R.string.settings_change_pw),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Spacer(modifier = modifier.padding(5.dp))
            }

            Column(
                modifier = modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PasswordField(
                    value = uiState.password,
                    onNewValue = viewModel::onPasswordChange,
                    errorMsg = uiState.passwordError,
                    modifier = modifier.fillMaxWidth(.85f)
                )
                RepeatPasswordField(
                    value = uiState.repeatPassword,
                    onNewValue = { viewModel.onRepeatPasswordChange(it) },
                    errorMsg = uiState.repeatPasswordError,
                    modifier = modifier.fillMaxWidth(.85f)
                )
            }

            Divider(
                modifier = modifier
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = dividerColor
            )

            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center
            ) {
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
                        action = { onSettingsRedirect() }
                    )
                    BasicTextButton(
                        text = R.string.delete_confirm,
                        modifier = modifier.fillMaxWidth(.6f),
                        color = deleteColor,
                        action = { viewModel.changePassword(onSettingsRedirect) }
                    )
                }
            }
        }
        Spacer(modifier = modifier)
    }
}

//@Preview
//@Composable
//fun ChangePwPreview() {
//    ChangePwScreen(onSettingsRedirect = {})
//}