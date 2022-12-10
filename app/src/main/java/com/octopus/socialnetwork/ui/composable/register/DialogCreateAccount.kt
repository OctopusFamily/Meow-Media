package com.octopus.socialnetwork.ui.composable.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.theme.Shapes
import com.octopus.socialnetwork.ui.theme.textSecondaryColor
import com.octopus.socialnetwork.ui.theme.textThirdColor


@Composable
fun DialogCreateAccount(
    modifier: Modifier = Modifier,
    checkEmail: () -> Unit,
    skip: () -> Unit,
) {
    Card(
        shape = Shapes.large,
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column {

            Image(
                Icons.Default.Email,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = Color.Gray
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.create_account_success),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.textSecondaryColor
                    ),
                )
                Text(
                    text = stringResource(R.string.create_account_message),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colors.textThirdColor

                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .background(MaterialTheme.colors.background),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = {
                    skip()
                }) {

                    Text(
                        stringResource(id = R.string.not_now),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.textThirdColor,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                Button(onClick = {
                    checkEmail()
                }) {
                    Text(
                        stringResource(id = R.string.check_email),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}