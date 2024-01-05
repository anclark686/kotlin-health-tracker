package com.reyaly.reyalyhealthtracker.screens.intake

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.components.ContentSection
import com.reyaly.reyalyhealthtracker.common.components.LogoBanner
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton
import com.reyaly.reyalyhealthtracker.screens.intake.components.BasicInfo
import com.reyaly.reyalyhealthtracker.screens.intake.components.HealthInfo
import com.reyaly.reyalyhealthtracker.screens.med.components.MedInfo
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch


@Composable
fun IntakeScreen(
    onUserAdded: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IntakeViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()

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
        }
        Column(modifier = modifier) {
            ContentSection(
                contentComposable = { BasicInfo() },
                text = R.string.intake_label_basic_info
            )

            ContentSection(
                contentComposable = { HealthInfo() },
                text = R.string.intake_label_health_info
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(
                text = R.string.submit,
                modifier = modifier,
                action = {
                    coroutineScope.launch {
                        viewModel.submitIntake(callback = { onUserAdded() })
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun IntakeScreenPreview() {
    IntakeScreen(
        onUserAdded = {}
    )
}