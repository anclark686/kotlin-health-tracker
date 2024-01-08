package com.reyaly.reyalyhealthtracker.screens.settings.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.screens.med.MedViewModel
import com.reyaly.reyalyhealthtracker.screens.settings.SettingsViewModel
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun UserProfileInfo(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var labelColor: Color
    var spinnerColor: Color

    if (isSystemInDarkTheme()) {
        labelColor = light_sky_blue
        spinnerColor = sky_blue
    } else {
        labelColor = dark_sky_blue
        spinnerColor = dark_sky_blue
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.getUser()
    }

    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),

    ) {
        when {
            uiState.userIsLoading -> {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = spinnerColor
                    )
                    Spacer(modifier = modifier.padding(10.dp))
                    Text(
                        text = stringResource(R.string.loading),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                }
            }
            uiState.user != null -> {
                Row(modifier = modifier.padding(vertical = 5.dp)) {
                    Text(
                        text = stringResource(R.string.settings_name),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    Text(
                        text = "${uiState.user?.firstName} ${uiState.user?.lastName}",
                        fontSize = 18.sp,
                    )
                }

                Row(modifier = modifier.padding(vertical = 5.dp)) {
                    Text(
                        text = stringResource(R.string.settings_email),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    uiState.user?.email?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                        )
                    }
                }

                Row(modifier = modifier.padding(vertical = 5.dp)) {
                    Text(
                        text = stringResource(R.string.settings_phone),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    uiState.user?.phoneNum?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                        )
                    }
                }

                Row(modifier = modifier.padding(vertical = 5.dp)) {
                    Text(
                        text = stringResource(R.string.settings_age),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    uiState.user?.let {
                        Text(
                            text = viewModel.getAge(uiState.user!!),
                            fontSize = 18.sp,
                        )
                    }
                }

                Row(modifier = modifier.padding(vertical = 5.dp)) {
                    Text(
                        text = stringResource(R.string.settings_joined),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    uiState.user?.joinedStr?.let {
                        Text(
                            text = it.toString(),
                            fontSize = 18.sp,
                        )
                    }
                }

                Row(modifier = modifier.padding(vertical = 5.dp)) {
                    Text(
                        text = stringResource(R.string.settings_height),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    uiState.user?.height?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                        )
                    }
                }

                Row(modifier = modifier.padding(vertical = 5.dp)) {
                    Text(
                        text = stringResource(R.string.settings_weight),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    uiState.user?.currWeight?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                        )
                    }
                }

                Row(modifier = modifier.padding(vertical = 5.dp)) {
                    Text(
                        text = stringResource(R.string.settings_weight_goals),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    uiState.user?.weightGoals?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                        )
                    }
                }

                Row(modifier = modifier.padding(vertical = 5.dp)) {
                    Text(
                        text = stringResource(R.string.settings_activity_level),
                        fontSize = 18.sp,
                        color = labelColor
                    )
                    Spacer(modifier = modifier.padding(5.dp))
                    uiState.user?.activityLevel?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                        )
                    }
                }
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(text = R.string.modify, modifier = modifier, action = { })
        }
    }
}

@Preview
@Composable
fun UserProfileInfoPreview() {
    UserProfileInfo()
}