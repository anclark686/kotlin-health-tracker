package com.reyaly.reyalyhealthtracker.screens.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.common.composable.SignInButton
import com.reyaly.reyalyhealthtracker.common.ext.textButton
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun SignInScreen(
    onEmailAndPwClick: () -> Unit,
    onSignUpRedirect: () -> Unit,
    modifier: Modifier = Modifier
) {
    var headerList: List<Color>
    var googleBackgroundColor: Color
    var googleTextColor: Color

    if (isSystemInDarkTheme()) {
        headerList = listOf<Color>(med_sky_blue, dark_sky_blue)
        googleBackgroundColor = Color.White
        googleTextColor = Color.Black
    } else {
        headerList = listOf<Color>(sky_blue, med_sky_blue)
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

        Column(
            modifier = modifier
                .fillMaxWidth(.75f)
                .padding(top = 20.dp, bottom = 10.dp)
        ) {
            SignInButton(
                text = R.string.sign_in_google,
                action = { /*TODO*/ },
                contentColor = googleBackgroundColor,
                textColor = googleTextColor,
                icon = R.drawable.ic_google
            )
            SignInButton(
                text = R.string.sign_in_email,
                action = { onEmailAndPwClick() },
                contentColor = med_sky_blue,
                icon = R.drawable.ic_mail
            )
            SignInButton(
                text = R.string.sign_in_phone,
                action = { /*TODO*/ },
                contentColor = dark_sky_blue,
                icon = R.drawable.ic_phone
            )
        }
        Column (
            modifier = modifier.padding(bottom = 30.dp)
        ) {
            BasicTextButton(R.string.sign_up_redirector, Modifier.textButton()) { onSignUpRedirect() }
        }
    }
}

@Preview
@Composable
fun SignInPreview() {
    SignInScreen(
        onEmailAndPwClick = { },
        onSignUpRedirect = {},
        modifier = Modifier
    )
}