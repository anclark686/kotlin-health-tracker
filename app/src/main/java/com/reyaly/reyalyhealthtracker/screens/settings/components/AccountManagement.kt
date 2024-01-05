package com.reyaly.reyalyhealthtracker.screens.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.screens.settings.SettingsViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue

@Composable
fun AccountManagement(
    openDialog: MutableState<Boolean>,
    onLogoutClick: () -> Unit,
    onLoginChangeClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    fun logout() {
        viewModel.onSignOut().also {
            onLogoutClick()
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier.padding(0.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextButton(
                text = R.string.settings_change_pw,
                modifier = modifier.padding(top = 10.dp),
                action = { onLoginChangeClick() },
            )
            BasicTextButton(
                text = R.string.settings_delete_account,
                modifier = modifier.padding(bottom = 10.dp),
                action = { openDialog.value = true },
                color = if (isSystemInDarkTheme()) errorPink else errorDarkRed
            )
        }
        Column(
            modifier = modifier.background(color = med_sky_blue),

            ) {
            Button(
                modifier = modifier.fillMaxSize(),
                shape = RectangleShape,
                onClick = { logout() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = med_sky_blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    stringResource(R.string.nav_logout),
                    fontSize = 18.sp,
                )
            }
        }
    }

}

@Preview
@Composable
fun AccountManagementPreview() {
    val openDeleteDialog = remember { mutableStateOf(false) }
    AccountManagement(
        openDialog = openDeleteDialog,
        onLogoutClick =  {  },
        onLoginChangeClick =  {  },
    )
}