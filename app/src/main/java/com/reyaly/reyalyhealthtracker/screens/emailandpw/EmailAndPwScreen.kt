package com.reyaly.reyalyhealthtracker.screens.emailandpw

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.common.composable.EmailField
import com.reyaly.reyalyhealthtracker.common.composable.PasswordField
import com.reyaly.reyalyhealthtracker.common.ext.textButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun EmailAndPasswordScreen(
    modifier: Modifier = Modifier,
    onSuccess: () -> Unit,
    viewModel: EmailAndPwViewModel = viewModel()
) {
//    val auth: FirebaseAuth = FirebaseAuth.getInstance()
//
//    if (auth.currentUser != null) {
//        onSuccess()
//    }

    val uiState by viewModel.uiState

    val focusManager = LocalFocusManager.current

    var headerList: List<Color>
    var backgroundColor: Color
    var shadowColor: Color
    var errorColor: Color

    if (isSystemInDarkTheme()) {
        headerList = listOf<Color>(med_sky_blue, dark_sky_blue)
        backgroundColor = dark_sky_blue
        shadowColor = Color.LightGray
        errorColor = errorPink
    } else {
        headerList = listOf<Color>(sky_blue, med_sky_blue)
        backgroundColor = light_sky_blue
        shadowColor = Color.Black
        errorColor = errorDarkRed
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
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    stringResource(R.string.login_details),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            if (viewModel.loginError != "") {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center

                ) {
                    Text(
                        viewModel.loginError,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = errorColor
                    )
                }
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
                modifier = modifier.padding(horizontal = 5.dp, vertical = 10.dp)
            ) {
                BasicButton(R.string.sign_in, Modifier.width(150.dp)) {
                    viewModel.onSignInClick(onSuccess = { onSuccess() })
                }
            }
            Column(
                modifier = modifier
            ) {
                BasicTextButton(R.string.forgot_password, Modifier.textButton()) {
                    viewModel.onForgotPasswordClick()
                }
            }
        }
        Spacer(modifier = modifier)

    }
}

//@Preview
//@Composable
//fun EAPPreview() {
//    EmailAndPasswordScreen(modifier = Modifier)
//}