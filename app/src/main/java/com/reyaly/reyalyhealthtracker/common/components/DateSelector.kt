package com.reyaly.reyalyhealthtracker.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.screens.AppViewModel
import com.reyaly.reyalyhealthtracker.screens.dashboard.DashboardViewModel
import java.time.LocalDate

@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(),
    initialDate: LocalDate? = null
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "login",
                modifier = modifier
                    .weight(.33f)
                    .width(250.dp)
                    .padding(15.dp)
                    .clickable{ viewModel.onDateChange("back") }
            )
            if (initialDate != null) {
                Text(
                    text = initialDate.format(uiState.formatter),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = uiState.dateChoice.format(uiState.formatter),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
            Image(
                painter = painterResource(R.drawable.ic_arrow_forward),
                contentDescription = "login",
                modifier = modifier
                    .weight(.33f)
                    .width(250.dp)
                    .padding(15.dp)
                    .clickable{ viewModel.onDateChange("forward") }
            )
        }
    }
}

@Preview
@Composable
fun DateSelectorPreview() {
    DateSelector(

    )
}