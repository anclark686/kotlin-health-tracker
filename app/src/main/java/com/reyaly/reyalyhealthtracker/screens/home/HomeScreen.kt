package com.reyaly.reyalyhealthtracker.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.screens.home.components.Descriptions
import com.reyaly.reyalyhealthtracker.screens.home.components.LoggedIn
import com.reyaly.reyalyhealthtracker.screens.home.components.LoggedOut
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue

@Composable
fun HomeScreen(
    onDashboardClick: () -> Unit,
    onLoginClick: () -> Unit,
    onIntakeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
//
//    if (auth.currentUser != null) {
//        onSuccess()
//    }
    var backgroundColor: Color

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
    } else {
        backgroundColor = light_sky_blue
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp)

    ) {
        Row(modifier = modifier.background(color = backgroundColor) ) {
            Image(
                painter = painterResource(R.drawable.rht_logo_words),
                contentDescription = "Logo",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
        Row(modifier = modifier) {
            Column {
                if (auth.currentUser != null)  {
                    LoggedIn({ onDashboardClick() })
                } else {
                    LoggedOut({ onLoginClick() })
                }
//                LoggedIn({ onDashboardClick() })
//                LoggedOut({ onLoginClick() })
                Button(
                    modifier = modifier,
                    onClick = {auth.signOut()}
                ) {
                    Text("Logout")
                }
                Button(
                    modifier = modifier,
                    onClick = { onIntakeClick() }
                ) {
                    Text("intake")
                }
            }
        }
        Row(modifier = modifier) {
            Descriptions()
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen(
        onDashboardClick = {},
        onLoginClick = {},
        onIntakeClick = {},
        modifier = Modifier.fillMaxSize()
    )
}