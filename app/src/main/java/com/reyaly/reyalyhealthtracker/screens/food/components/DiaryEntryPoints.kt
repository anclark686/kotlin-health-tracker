package com.reyaly.reyalyhealthtracker.screens.food.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicTextButton

@Composable
fun DiaryEntryPoints(
    onBreakfastClick: () -> Unit,
    onLunchClick: () -> Unit,
    onDinnerClick: () -> Unit,
    onSnackClick: () -> Unit,
    onWaterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = modifier) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_breakfast),
                    contentDescription = "breakfast",
                    modifier = modifier
                        .width(30.dp)
                        .padding(horizontal = 5.dp)
                        .clickable { onBreakfastClick() }
                )
                BasicTextButton(
                    text = R.string.nav_breakfast,
                    modifier = modifier,
                    action = { onBreakfastClick() }
                )
            }

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_lunch),
                    contentDescription = "lunch",
                    modifier = modifier
                        .width(30.dp)
                        .padding(horizontal = 5.dp)
                        .clickable { onLunchClick() }
                )
                BasicTextButton(
                    text = R.string.nav_lunch,
                    modifier = modifier,
                    action = { onLunchClick() }
                )
            }

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_dinner),
                    contentDescription = "dinner",
                    modifier = modifier
                        .width(30.dp)
                        .padding(horizontal = 5.dp)
                        .clickable { onDinnerClick() }
                )
                BasicTextButton(
                    text = R.string.nav_dinner,
                    modifier = modifier,
                    action = { onDinnerClick() }
                )
            }

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_snack),
                    contentDescription = "snack",
                    modifier = modifier
                        .width(30.dp)
                        .padding(horizontal = 5.dp)
                        .clickable { onSnackClick() }
                )
                BasicTextButton(
                    text = R.string.nav_snack,
                    modifier = modifier,
                    action = { onSnackClick() }
                )
            }

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
                ) {
                Image(
                    painter = painterResource(R.drawable.ic_water_glass),
                    contentDescription = "water",
                    modifier = modifier
                        .width(30.dp)
                        .padding(horizontal = 5.dp)
                        .clickable { onWaterClick() }
                )
                BasicTextButton(
                    text = R.string.nav_water,
                    modifier = modifier,
                    action = { onWaterClick() }
                )
            }
        }
    }
}

@Preview
@Composable
fun DiaryEntryPointsPreview() {
    DiaryEntryPoints(
        onBreakfastClick = {},
        onLunchClick = {},
        onDinnerClick = {},
        onSnackClick = {},
        onWaterClick = {},
    )
}