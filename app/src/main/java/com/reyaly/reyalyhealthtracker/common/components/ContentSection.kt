package com.reyaly.reyalyhealthtracker.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.screens.dashboard.components.DashboardInfo
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.light_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun ContentSection(
    contentComposable: @Composable () -> Unit,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    var backgroundColor: Color
    var shadowColor: Color
    var colorList: List<Color>

    if (isSystemInDarkTheme()) {
        backgroundColor = dark_sky_blue
        colorList = listOf<Color>(dark_sky_blue, med_sky_blue, dark_sky_blue)
        shadowColor = Color.LightGray
    } else {
        backgroundColor = light_sky_blue
        colorList = listOf<Color>(med_sky_blue, sky_blue, med_sky_blue)
        shadowColor = Color.Black
    }

    Column(
        modifier = modifier
            .padding(start = 40.dp, top = 40.dp, end = 40.dp)
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
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }


        contentComposable()
    }
}

@Preview
@Composable
fun ContentSectionPreview() {
    ContentSection(
        text = R.string.dashboard_stats,
        contentComposable = { DashboardInfo() }
    )
}