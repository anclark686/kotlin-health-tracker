package com.reyaly.reyalyhealthtracker.screens.signup

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicField
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.common.composable.EmailField
import com.reyaly.reyalyhealthtracker.common.composable.PasswordField
import com.reyaly.reyalyhealthtracker.common.composable.RepeatPasswordField
import com.reyaly.reyalyhealthtracker.common.ext.textButton
import com.reyaly.reyalyhealthtracker.screens.emailandpw.EmailAndPwViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun SignUpScreen(
    onSignInRedirect: () -> Unit,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = viewModel()
) {
    val uiState by viewModel.uiState

    val focusManager = LocalFocusManager.current

    var headerList: List<Color>
    var backgroundColor: Color
    var shadowColor: Color

    if (isSystemInDarkTheme()) {
        headerList = listOf<Color>(med_sky_blue, dark_sky_blue)
        backgroundColor = dark_sky_blue
        shadowColor = Color.LightGray
    } else {
        headerList = listOf<Color>(sky_blue, med_sky_blue)
        backgroundColor = light_sky_blue
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

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(headerList)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = modifier.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.rhc_logo_no_background),
                    contentDescription = "logo",
                    modifier = modifier
                        .width(75.dp)
                        .padding(15.dp)
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
            Divider(
                modifier = modifier
                    .fillMaxWidth(),
                thickness = 2.dp,
                color = dark_sky_blue
            )
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
                modifier = modifier.padding(horizontal = 5.dp, vertical = 20.dp),
            ) {
                Text(
                    stringResource(R.string.create_account),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            Column(
                modifier = modifier.padding(5.dp)
            ) {
                BasicField(
                    R.string.name,
                    value = uiState.name,
                    onNewValue = viewModel::onNameChange,
                    errorMsg = uiState.nameError,
                    modifier = modifier.fillMaxWidth(.80f)
                )
            }
            Column(
                modifier = modifier.padding(5.dp)
            ) {
                EmailField(
                    value = uiState.email,
                    onNewValue = viewModel::onEmailChange,
                    errorMsg = uiState.emailError,
                    modifier = modifier.fillMaxWidth(.80f)
                )
            }
            Column(
                modifier = modifier.padding(5.dp)
            ) {
                PasswordField(
                    value = uiState.password,
                    onNewValue = viewModel::onPasswordChange,
                    errorMsg = uiState.passwordError,
                    modifier = modifier.fillMaxWidth(.80f)
                )
            }
            Column(
                modifier = modifier.padding(5.dp)
            ) {
                RepeatPasswordField(
                    value = uiState.repeatPassword,
                    onNewValue = { viewModel.onRepeatPasswordChange(it) },
                    errorMsg = uiState.repeatPasswordError,
                    modifier = modifier.fillMaxWidth(.80f)
                )
            }

            Column(
                modifier = modifier.padding(horizontal = 5.dp, vertical = 10.dp)
            ) {
                BasicButton(R.string.sign_up, Modifier.width(150.dp)) {
                    viewModel.onSignUpClick(onSuccess = { onSuccess() })
                }
            }
            Column (
                modifier = modifier.padding(bottom = 10.dp)
            ) {
                BasicTextButton(R.string.sign_in_redirector, Modifier.textButton()) { onSignInRedirect() }
            }
        }
        Spacer(modifier = modifier)


    }
}

//@Preview
//@Composable
//fun SignUpPreview() {
//    SignUpScreen(onSignInRedirect = {})
//}