/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.reyaly.reyalyhealthtracker.common.composable

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaly.reyalyhealthtracker.R
import com.reyaly.reyalyhealthtracker.ui.theme.dark_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.google_blue
import com.reyaly.reyalyhealthtracker.ui.theme.med_sky_blue
import com.reyaly.reyalyhealthtracker.ui.theme.sky_blue

@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit, color: Color = Color.Unspecified) {
  TextButton(onClick = action, modifier = modifier) {
    Text(
      text = stringResource(text),
      textAlign = TextAlign.Center,
      color = color,
      fontSize = 18.sp,
    )
  }
}

@Composable
fun BasicButton(@StringRes text: Int, action: () -> Unit, modifier: Modifier = Modifier) {
  Button(
    onClick = action,
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(
      containerColor = med_sky_blue,
      contentColor = Color.White
    )
  ) {
    Text(
      text = stringResource(text),
      fontSize = 18.sp,
      textAlign = TextAlign.Center)
  }
}

@Composable
fun SignInButton(
  @StringRes text: Int,
  action: () -> Unit,
  contentColor: Color,
  @DrawableRes icon: Int,
  modifier: Modifier = Modifier,
  textColor: Color = Color.White
) {
  Column(
    modifier = modifier.padding(3.dp)
  ) {
    Button(
      onClick = action,
      modifier = modifier
        .fillMaxWidth(),
      shape = RoundedCornerShape(5.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = contentColor,
        contentColor = Color.White
      ),
      contentPadding = PaddingValues(0.dp)
    ) {
      Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Image(
          painter = painterResource(icon),
          contentDescription = "scale",
          modifier = modifier
            .width(50.dp)
            .padding(10.dp)
        )
        Column (
          modifier = modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Text(
            text = "${stringResource(R.string.sign_in_with)} ${stringResource(text)}",
            fontSize = 18.sp,
            color = textColor,
            textAlign = TextAlign.Center
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun SignInButtonPreview() {
  SignInButton(text = R.string.nav_sign_in, action = { /*TODO*/ }, contentColor = google_blue, icon = R.drawable.ic_google)
}

@Preview
@Composable
fun SignInButtonPreview2() {
  SignInButton(text = R.string.nav_sign_in, action = { /*TODO*/ }, contentColor = med_sky_blue, icon = R.drawable.ic_mail)
}

@Preview
@Composable
fun SignInButtonPreview3() {
  SignInButton(text = R.string.nav_sign_in, action = { /*TODO*/ }, contentColor = dark_sky_blue, icon = R.drawable.ic_phone)
}

@Composable
fun DashboardButton(modifier: Modifier = Modifier, onDashboardClick: () -> Unit) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxWidth()
      .padding(5.dp)
  ) {
    BasicButton(R.string.text_dashboard, action = {onDashboardClick()})
  }
}

@Preview
@Composable
fun DashboardButtonPreview3() {
  DashboardButton(modifier = Modifier, onDashboardClick = {})
}

@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
  Button(
    onClick = action,
    colors = ButtonDefaults.buttonColors(
      containerColor = med_sky_blue,
      contentColor = Color.White
    )
  ) {
    Text(text = stringResource(text))
  }
}

@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
  Button(
    onClick = action,
    colors = ButtonDefaults.buttonColors(
      containerColor = med_sky_blue,
      contentColor = Color.White
    )
  ) {
    Text(text = stringResource(text))
  }
}
