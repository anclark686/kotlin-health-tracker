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

@Composable
fun SettingsScreen(
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onLoginChangeClick: () -> Unit,
    onLoginDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    fun logout() {
        viewModel.onSignOut().also {
            onLogoutClick()
        }
    }

    var backgroundColor: Color
    var shadowColor: Color
    var deleteColor: Color
    var colorList: List<Color>

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        shadowColor = Color.LightGray
        deleteColor = errorPink
        colorList = listOf<Color>(dark_sky_blue, med_sky_blue, dark_sky_blue)
    } else {
        backgroundColor = light_sky_blue
        shadowColor = Color.Black
        deleteColor = errorDarkRed
        colorList = listOf<Color>(med_sky_blue, sky_blue, med_sky_blue)
    }

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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = modifier
                        .background(Brush.horizontalGradient(colorList))
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.settings_profile),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                UserProfileInfo()
            }

            Column(
                modifier = modifier
                    .padding(start = 40.dp, end = 40.dp, bottom = 40.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(8.dp),
                        spotColor = shadowColor
                    )
                    .background(color = backgroundColor),
                verticalArrangement = Arrangement.SpaceBetween,

            ) {
                Column(
                    modifier = modifier
                        .background(Brush.horizontalGradient(colorList))
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.settings_account),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = modifier.padding(0.dp),
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
                        action = { openDeleteDialog.value = true },
                        color = deleteColor
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