package com.reyaly.reyalyhealthtracker.screens.resetemail

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.common.ext.textButton
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun ResetEmailSentScreen(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
    ) {
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
                    painter = painterResource(R.drawable.rht_logo_no_background),
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
                modifier = modifier
                    .padding(vertical = 25.dp, horizontal = 20.dp)
            ) {
                Text(
                    stringResource(R.string.recovery_email_sent),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                Text(
                    stringResource(R.string.recovery_check_inbox),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                BasicTextButton(
                    text = R.string.recovery_return,
                    modifier = modifier.textButton(),
                    action = { onLoginClick() }
                )
            }

        }
        Spacer(modifier = modifier)
    }
}

@Preview
@Composable
fun ResetEmailSentScreenPreview() {
    ResetEmailSentScreen(onLoginClick = {})
}