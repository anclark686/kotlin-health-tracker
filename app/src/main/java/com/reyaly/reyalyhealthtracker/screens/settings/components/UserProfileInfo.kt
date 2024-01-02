package com.reyaly.reyalyhealthtracker.screens.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.common.composable.BasicButton

@Composable
fun UserProfileInfo(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),

    ) {
        Row(modifier = modifier.padding(vertical = 5.dp)) {
            Text(
                text = stringResource(R.string.settings_name),
                fontSize = 18.sp,
            )
            Spacer(modifier = modifier.padding(5.dp))
            Text(
                text = "**NAME**",
                fontSize = 18.sp,
            )
        }

        Row(modifier = modifier.padding(vertical = 5.dp)) {
            Text(
                text = stringResource(R.string.settings_email),
                fontSize = 18.sp,
            )
            Spacer(modifier = modifier.padding(5.dp))
            Text(
                text = "**EMAIL**",
                fontSize = 18.sp,
            )
        }

        Row(modifier = modifier.padding(vertical = 5.dp)) {
            Text(
                text = stringResource(R.string.settings_phone),
                fontSize = 18.sp,
            )
            Spacer(modifier = modifier.padding(5.dp))
            Text(
                text = "**PHONE**",
                fontSize = 18.sp,
            )
        }

        Row(modifier = modifier.padding(vertical = 5.dp)) {
            Text(
                text = stringResource(R.string.settings_birthday),
                fontSize = 18.sp,
            )
            Spacer(modifier = modifier.padding(5.dp))
            Text(
                text = "**BIRTHDAY**",
                fontSize = 18.sp,
            )
        }

        Row(modifier = modifier.padding(vertical = 5.dp)) {
            Text(
                text = stringResource(R.string.settings_sex),
                fontSize = 18.sp,
            )
            Spacer(modifier = modifier.padding(5.dp))
            Text(
                text = "**SEX**",
                fontSize = 18.sp,
            )
        }

        Row(modifier = modifier.padding(vertical = 5.dp)) {
            Text(
                text = stringResource(R.string.settings_gender),
                fontSize = 18.sp,
            )
            Spacer(modifier = modifier.padding(5.dp))
            Text(
                text = "**GENDER**",
                fontSize = 18.sp,
            )
        }

        Row(modifier = modifier.padding(vertical = 5.dp)) {
            Text(
                text = stringResource(R.string.settings_joined),
                fontSize = 18.sp,
            )
            Spacer(modifier = modifier.padding(5.dp))
            Text(
                text = "**JOINED**",
                fontSize = 18.sp,
            )
        }
        Column(
            modifier = modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(text = R.string.settings_modify, modifier = modifier) {
                
            }
        }
    }
}

@Preview
@Composable
fun UserProfileInfoPreview() {
    UserProfileInfo()
}