package com.reyaly.reyalyhealthtracker.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.DeleteModal
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.screens.settings.components.UserProfileInfo
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.errorDarkRed
import com.reyaly.reyalyhealthtracker.ui.theme.errorPink
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.screens.settings.components.AccountManagement

@Composable
fun SettingsScreen(
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onLoginChangeClick: () -> Unit,
    onLoginDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    val openDeleteDialog = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        DeleteModal(
            openDialog = openDeleteDialog,
            youSure = R.string.settings_delete_sure,
            warning = R.string.settings_delete_permanent,
            onAction = { viewModel.deleteAccount(onSignInRedirect = { onLoginDeleteClick() })}
        )

        LogoBanner()

        if (viewModel.auth.currentUser != null) {
            ContentSection(
                contentComposable = { UserProfileInfo() },
                text = R.string.settings_profile)

            ContentSection(
                contentComposable = { AccountManagement(
                    openDialog = openDeleteDialog,
                    onLogoutClick =  { onLogoutClick() },
                    onLoginChangeClick = { onLoginChangeClick ()},
                ) },
                text = R.string.settings_account
            )

            Spacer(modifier = modifier.padding(20.dp))
        } else {
            Column(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    modifier = modifier,
                    onClick = { onLoginClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = med_sky_blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.nav_sign_in), fontSize = 30.sp)
                }
            }
            Spacer(modifier = modifier.padding(20.dp))
        }

    }
}

//@Preview
//@Composable
//fun SettingsPreview() {
//    SettingsScreen(
//        onDashboardClick = {},
//        onLogoutClick = {},
//        modifier = Modifier
//    )
//}