package com.reyaly.reyalyhealthtracker.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.screens.emailandpw.EmailAndPwViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue

@Composable
fun SettingsScreen(
    onDashboardClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
//    viewModel: SettingsViewModel = viewModel()
) {

//    fun logout() {
//        viewModel.onSignOut().also {
//            onLogoutClick()
//        }
//    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Button(
                modifier = modifier.padding(25.dp),
                onClick = { onDashboardClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = med_sky_blue,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.text_dashboard), fontSize = 20.sp)
            }
        }
        Text(
            text = stringResource(R.string.text_settings),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "**NAME**",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "**EMAIL**",
            style = MaterialTheme.typography.headlineSmall
        )
        Button(
            modifier = modifier.padding(25.dp),
            onClick = {  },
            colors = ButtonDefaults.buttonColors(
                containerColor = med_sky_blue,
                contentColor = Color.White
            )
        ) {
            Text(stringResource(R.string.nav_logout), fontSize = 20.sp)
        }

    }
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsScreen(
        onDashboardClick = {},
        onLogoutClick = {},
        modifier = Modifier
    )
}