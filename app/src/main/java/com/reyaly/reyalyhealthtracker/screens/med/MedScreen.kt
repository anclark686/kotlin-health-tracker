package com.reyaly.reyalyhealthtracker.screens.med

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.common.composable.DashboardButton
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.screens.med.components.MedInfo
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.common.components.DateSelector

@Composable
fun MedScreen(
    onDashboardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = modifier) {
            LogoBanner()
            DashboardButton(modifier = modifier, onDashboardClick = { onDashboardClick() })
        }

//        DateSelector()

        ContentSection(
            contentComposable = { MedInfo() },
            text = R.string.intake_label_med_info
        )

        Spacer(modifier = modifier.padding(20.dp))
    }

}

@Preview
@Composable
fun MedPreview() {
    MedScreen(
        onDashboardClick = {},
        modifier = Modifier
    )
}