package com.reyaly.reyalyhealthtracker.screens.med

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
import com.reyaly.reyalyhealthtracker.helpers.changeDate
import com.reyaly.reyalyhealthtracker.screens.med.components.AddEditMedModal
import java.time.LocalDate

@Composable
fun MedScreen(
    onDashboardClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MedViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val medState by viewModel.medState.collectAsState()

    val focusManager = LocalFocusManager.current

    val openAddModal = remember { mutableStateOf(false) }

    var date = remember { mutableStateOf(LocalDate.now() ) }

    suspend fun onDateChange(direction: String) {
        date = changeDate(date, direction)
        viewModel.getUsersMeds(date.value)
    }

    AddEditMedModal(date, openAddModal)

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
            DateSelector(initialDate = date, onChange = ::onDateChange)
            DashboardButton(modifier = modifier, onDashboardClick = { onDashboardClick() })
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.padding(20.dp))
            Image(
                painter = painterResource(R.drawable.ic_pills),
                contentDescription = "pills",
                modifier = modifier
                    .width(150.dp)
                    .padding(horizontal = 5.dp)
            )
            Spacer(modifier = modifier.padding(5.dp))
            BasicButton(
                text = R.string.add_new,
                modifier = modifier,
                action = { openAddModal.value = true }
            )
        }

        ContentSection(
            contentComposable = { MedInfo(date) },
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