package com.octopus.socialnetwork.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.CustomButton
import com.octopus.socialnetwork.ui.composable.CustomSnackBar
import com.octopus.socialnetwork.ui.composable.LoadingDialog
import com.octopus.socialnetwork.ui.composable.SpacerVertical32
import com.octopus.socialnetwork.ui.composable.TextWithAction
import com.octopus.socialnetwork.ui.composable.register.DialogCreateAccount
import com.octopus.socialnetwork.ui.composable.register.AccountInformation
import com.octopus.socialnetwork.ui.composable.register.PersonalInformation
import com.octopus.socialnetwork.ui.composable.register.StepIndicatorRegistration
import com.octopus.socialnetwork.ui.screen.login.navigateToLogin
import com.octopus.socialnetwork.ui.screen.register.uistate.RegisterUiState
import com.octopus.socialnetwork.ui.theme.SocialNetworkTheme
import com.octopus.socialnetwork.ui.theme.spacingMedium
import com.octopus.socialnetwork.ui.theme.textSecondaryColor
import com.octopus.socialnetwork.ui.theme.textThirdColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(state.initPage)
    val coroutineScope = rememberCoroutineScope()

    RegisterContent(
        state = state, pagerState = pagerState,
        onClickRegister = viewModel::register,
        coroutineScope = coroutineScope,
        showError = viewModel::showError,
        onSuccessCreateAccount = viewModel::onSuccessCreateAccount,
        onFailedCreateAccount = viewModel::onFailedCreateAccount,
        onChangeUserName = viewModel::onChangeUserName,
        onChangeEmail = viewModel::onChangeEmail,
        onChangeReEmail = viewModel::onChangeReEmail,
        onChangePassword = viewModel::onChangePassword,
        onChangeFirstName = viewModel::onChangeFirstName,
        onChangeLastName = viewModel::onChangeLastName,
        onChangeGender = viewModel::onChangeGender,
        onChangeBirthday = viewModel::onChangeBirthday,
        onClickLogin = {
            navController.navigateToLogin()
        },
    )
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
private fun RegisterContent(
    state: RegisterUiState,
    pagerState: PagerState,
    onClickRegister: () -> Unit,
    onClickLogin: () -> Unit,
    coroutineScope: CoroutineScope,
    showError: (Int) -> Unit,
    onSuccessCreateAccount: () -> Unit,
    onFailedCreateAccount: () -> Unit,
    onChangeUserName: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangeReEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeFirstName: (String) -> Unit,
    onChangeLastName: (String) -> Unit,
    onChangeGender: (String) -> Unit,
    onChangeBirthday: (String) -> Unit,
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(spacingMedium)
            .fillMaxSize()
            .padding(top = spacingMedium, start = spacingMedium, end = spacingMedium)

            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {

        Text(
            stringResource(id = R.string.create_account),
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.textSecondaryColor
            )
        )

        Text(
            stringResource(id = R.string.sig_up_note),
            style = MaterialTheme.typography.caption.copy(
                color = MaterialTheme.colors.textThirdColor
            )
        )

        StepIndicatorRegistration(pagerState.currentPage)

        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = painterResource(R.drawable.signup),
            contentDescription = stringResource(R.string.image_register)
        )
        HorizontalPager(
            count = 2,
            state = pagerState,
            userScrollEnabled = false,
        ) { page ->
            when (page) {
                0 -> {
                    AccountInformation(
                        state.userInfoForm,
                        showError = state.displayErrorsFirstStepRegistration,
                        onChangeUserName = onChangeUserName,
                        onChangeEmail = onChangeEmail,
                        onChangeReEmail = onChangeReEmail,
                        onChangePassword = onChangePassword,
                    )
                }

                1 -> {
                    PersonalInformation(
                        state.userInfoForm,
                        showError = state.displayErrorsSecondStepRegistration,
                        onChangeFirstName = onChangeFirstName,
                        onChangeLastName = onChangeLastName,
                        onChangeGender = onChangeGender,
                        onChangeBirthday = onChangeBirthday,
                    )
                }

            }

        }

        SpacerVertical32()


        CustomButton(
            text = stringResource(if (pagerState.currentPage == state.initPage) R.string.next else R.string.create_account)
        ) {
            val userInput = state.userInfoForm
            if (pagerState.currentPage == state.initPage) {

                if (userInput.userName.isValid && userInput.email.isValid && userInput.reEmail.isValid && userInput.password.isValid) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                } else {
                    showError(1)
                }

            } else {
                if (userInput.firstName.isValid && userInput.lastName.isValid && userInput.gender.isValid && userInput.birthDate.isValid) {
                    onClickRegister()
                } else {
                    showError(2)
                }

            }


        }

        Box(
            Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            TextWithAction(
                text = stringResource(R.string.already_have_an_account),
                textAction = stringResource(R.string.login),
                onClick = onClickLogin
            )


        }

    }



    if (state.failedCreateAccount) {
        CustomSnackBar(
            message = stringResource(id = R.string.failed_create_account),
            onFailedCreateAccount
        )
    }

    if (state.isLoading) {
        LoadingDialog()
    }

    if (state.isSuccess) {
        Dialog(onDismissRequest = { }) {
            DialogCreateAccount(
                checkEmail = {
                    onSuccessCreateAccount()
                    onClickLogin()
                },
                skip = {
                    onSuccessCreateAccount()
                }
            )
        }

    }


}


@Preview
@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun RegisterScreenPreview() {
    SocialNetworkTheme {
        Surface {

        }
    }
}