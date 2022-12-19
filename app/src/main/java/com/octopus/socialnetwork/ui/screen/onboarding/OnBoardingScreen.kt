package com.octopus.socialnetwork.ui.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.CustomButton
import com.octopus.socialnetwork.ui.composable.TextWithAction
import com.octopus.socialnetwork.ui.screen.login.navigateToLogin
import com.octopus.socialnetwork.ui.screen.register.navigateToRegister
import com.octopus.socialnetwork.ui.theme.spacingExtraLarge
import com.octopus.socialnetwork.ui.theme.spacingHuge
import com.octopus.socialnetwork.ui.theme.spacingMedium
import com.octopus.socialnetwork.ui.theme.textPrimaryColor


@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {

    OnBoardingContent(
        onClickLogin = {
            navController.navigateToLogin()
        },
        onClickCreateAccount = {
            navController.navigateToRegister()
        }
    )
}

@Composable
private fun OnBoardingContent(
    onClickLogin: () -> Unit,
    onClickCreateAccount: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(vertical = spacingExtraLarge)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.ic_onboarding),
            contentDescription = stringResource(R.string.icon_on_boarding),
            modifier = Modifier.padding(top = 64.dp)
        )

        Text(
            text = stringResource(R.string.on_boarding_text), textAlign = TextAlign.Start,
            color = MaterialTheme.colors.textPrimaryColor, style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(top = spacingHuge, start = spacingMedium)
        )

        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            modifier = Modifier.padding(horizontal = spacingMedium),
            text = stringResource(R.string.create_account),
            onClick = onClickCreateAccount
        )
        TextWithAction(
            text = stringResource(R.string.already_have_an_account),
            textAction = stringResource(R.string.login),
            onClick = onClickLogin
        )

    }

}

