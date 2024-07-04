package com.reyaly.reyalyhealthtracker.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateSelector(
    initialDate: MutableState<LocalDate>,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    val headerColor = if (isSystemInDarkTheme()) {
        med_sky_blue
    } else {
        sky_blue
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = headerColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_left_arrow),
                contentDescription = "left-arrow",
                modifier = modifier
                    .width(80.dp)
                    .padding(15.dp)
                    .clickable { onChange("subtract") },
            )
            Spacer(modifier = modifier.padding(20.dp))
            Text(
                text = initialDate.value.format(formatter),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.padding(20.dp))
            Image(
                painter = painterResource(R.drawable.ic_right_arrow),
                contentDescription = "right-arrow",
                modifier = modifier
                    .width(80.dp)
                    .padding(15.dp)
                    .clickable { onChange("add") }
            )
        }
    }
}

@Preview
@Composable
fun DateSelectorPreview() {
    var date = remember { mutableStateOf(LocalDate.now() ) }
    DateSelector(
        initialDate = date,
        onChange = { }
    )
}