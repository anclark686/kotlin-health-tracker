package com.reyaly.reyalyhealthtracker.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.screens.home.components.Descriptions
import com.reyaly.reyalyhealthtracker.screens.home.components.LoggedIn
import com.reyaly.reyalyhealthtracker.screens.home.components.LoggedOut

@Composable
fun HomeScreen(
//    userViewModel: UserViewModel,
    onDashboardClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 6.dp, horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp)

    ) {
        Row(modifier = modifier) {
            Text(
                text = stringResource(R.string.home_header),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }
        Row(modifier = modifier ) {
            Image(
                painter = painterResource(R.drawable.reyaly_health_tracker),
                contentDescription = "Logo",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
        Row(modifier = modifier) {
            Column(
            ) {
//                if (2+2 == 4) {
////                    if (userViewModel.userIsAuthenticated) {
//                    LoggedIn({ onDashboardClick() })
//                } else {
//                    LoggedOut({ onLoginClick() })
//                }
                LoggedIn({ onDashboardClick() })
                LoggedOut({ onLoginClick() })
            }
        }
        Row(modifier = modifier) {
            Descriptions()
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen(
        onDashboardClick = {},
        onLoginClick = {},
        modifier = Modifier.fillMaxSize()
    )
}