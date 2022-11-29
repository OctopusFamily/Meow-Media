package com.octopus.socialnetwork.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.CustomButton
import com.octopus.socialnetwork.ui.composable.SpacerVertical16
import com.octopus.socialnetwork.ui.composable.TextWithAction
import com.octopus.socialnetwork.ui.screen.register.composable.FirstForm
import com.octopus.socialnetwork.ui.screen.register.composable.SecondForm
import com.octopus.socialnetwork.ui.screen.register.composable.StepText

@ExperimentalPagerApi
@Preview(showSystemUi = true)
@Composable
fun RegisterScreen() {
    val pager = rememberPagerState(0)

    RegisterContent(pager)
}

@ExperimentalPagerApi
@Composable
private fun RegisterContent(pager: PagerState) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        ) {

        Text(
            stringResource(id = R.string.create_account),
            style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
        )

        Text(
            stringResource(id = R.string.sig_up_note),
            style = TextStyle(fontSize = 12.sp, color = Color.Gray)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),

            Arrangement.Center, verticalAlignment = Alignment.CenterVertically
        ) {

            StepText("1", (pager.currentPage == 0 || pager.currentPage == 1))

            Divider(
                modifier = Modifier
                    .width(96.dp)
                    .padding(horizontal = 2.dp), color = Color.Gray
            )
            StepText("2", pager.currentPage == 1)

        }
        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = painterResource(R.drawable.signup),
            contentDescription = "image"
        )
        HorizontalPager(
            count = 2,
            state = pager,
        ) { page ->
            when (page) {
                0 -> {
                    FirstForm()
                }

                1 -> {
                    SecondForm()
                }

            }

        }

    }

    Box(
        Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomButton(
                text = stringResource(if (pager.currentPage == 0) R.string.next else R.string.create_account),
                onClick = {}
            )
            SpacerVertical16()
            TextWithAction(
                text = stringResource(R.string.already_have_an_account),
                textAction = stringResource(R.string.login),
                onClick = {}
            )

        }

    }
}

