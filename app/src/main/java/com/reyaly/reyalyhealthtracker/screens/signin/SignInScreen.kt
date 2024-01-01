package com.reyaly.reyalyhealthtracker.screens.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.SignInButton
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner

@Composable
fun SignInScreen(
    onEmailAndPwClick: () -> Unit,
    onEmailAndPwClickDelete: () -> Unit,
    onEmailAndPwClickChange: () -> Unit,
    onSignInClick: () -> Unit,
    onResetPassword: () -> Unit,
    onDeleteAccount: () -> Unit,
    modifier: Modifier = Modifier,
    modify: String? = null
) {
    
    var googleBackgroundColor: Color
    var googleTextColor: Color

    if (isSystemInDarkTheme()) {
        googleBackgroundColor = Color.White
        googleTextColor = Color.Black
    } else {
        googleBackgroundColor = Color.Black
        googleTextColor = Color.White
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoBanner()

        Column(
            modifier = modifier.padding(top = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_login),
                contentDescription = "login",
                modifier = modifier
                    .width(250.dp)
                    .padding(15.dp)
            )
        }

        if (modify != null) {
            Column(
                modifier = modifier
                    .fillMaxWidth(.75f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.reauthenticate),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth(.75f)
                .padding(top = 20.dp, bottom = 10.dp)
        ) {
            if (modify != "change") {
                SignInButton(
                    text = R.string.sign_in_google,
                    action = { onSignInClick() },
                    contentColor = googleBackgroundColor,
                    textColor = googleTextColor,
                    icon = R.drawable.ic_google
                )
            } else {
                Text(
                    stringResource(R.string.sign_in_no_google),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(10.dp),
                )
            }

            if (modify == "delete") {
                SignInButton(
                    text = R.string.sign_in_email,
                    action = { onEmailAndPwClickDelete() },
                    contentColor = med_sky_blue,
                    icon = R.drawable.ic_mail
                )
            } else if (modify == "change") {
                SignInButton(
                    text = R.string.sign_in_email,
                    action = { onEmailAndPwClickChange() },
                    contentColor = med_sky_blue,
                    icon = R.drawable.ic_mail
                )
            } else {
                SignInButton(
                    text = R.string.sign_in_email,
                    action = { onEmailAndPwClick() },
                    contentColor = med_sky_blue,
                    icon = R.drawable.ic_mail
                )
            }

        }
        Spacer(modifier = modifier.padding(20.dp))
    }
}

//@Preview
//@Composable
//fun SignInPreview() {
//    SignInScreen(
//        onEmailAndPwClick = { },
//        modifier = Modifier
//    )
//}