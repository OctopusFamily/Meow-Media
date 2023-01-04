package com.octopus.socialnetwork.ui.screen.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.CustomButton
import com.octopus.socialnetwork.ui.composable.EditProfileInformation
import com.octopus.socialnetwork.ui.composable.InputTextField
import com.octopus.socialnetwork.ui.composable.SpacerVertical32
import com.octopus.socialnetwork.ui.composable.lotties.LottieLoading
import com.octopus.socialnetwork.ui.screen.edit_profile.state.EditProfileUiState
import com.octopus.socialnetwork.ui.theme.LightBlack_65
import com.octopus.socialnetwork.ui.theme.spacingExtraLarge
import com.octopus.socialnetwork.ui.theme.spacingMedium

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let { viewModel.onClickChangeImage(it) } })

    val changeCoverImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let { viewModel.changeCoverImage(it) } }
    )
    EditProfileContent(
        state = state,
        onChangeFirstName = viewModel::onChangeFirstName,
        onChangeLastName = viewModel::onChangeLastName,
        onChangeEmail = viewModel::onChangeEmail,
        onChangeCurrentPassword = viewModel::onChangeCurrentPassword,
        onChangeNewPassword = viewModel::onChangeNewPassword,
        onClickSave = viewModel::onClickSave,
        onClickBack = { navController.popBackStack() },
        onClickShowCurrentPassword = viewModel::onChangeCurrentPasswordVisibility,
        onClickShowNewPassword = viewModel::onChangeNewPasswordVisibility,
        onChangeCoverProfile = {
            changeCoverImage.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onChangeProfileImage = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
    )
}

@Composable
private fun EditProfileContent(
    state: EditProfileUiState,
    onChangeFirstName: (String) -> Unit,
    onChangeLastName: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangeCurrentPassword: (String) -> Unit,
    onChangeNewPassword: (String) -> Unit,
    onClickSave: () -> Unit,
    onClickBack: () -> Unit,
    onClickShowCurrentPassword: () -> Unit,
    onClickShowNewPassword: () -> Unit,
    onChangeCoverProfile: () -> Unit,
    onChangeProfileImage: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        if (state.isLoading) {
            LottieLoading()
        } else {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .zIndex(1f),
                contentAlignment = Alignment.TopEnd
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f)
                        .padding(spacingMedium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    ConstraintLayout() {
                        val (circle, arrowBack) = createRefs()
                        IconButton(
                            onClick = onClickBack,
                            Modifier
                                .clip(CircleShape)
                                .background(color = LightBlack_65)
                                .size(32.dp)
                                .constrainAs(circle) {},
                        ) {}
                        Icon(
                            androidx.compose.material.icons.Icons.Default.ArrowBackIos,
                            contentDescription = stringResource(id = R.string.back),
                            tint = Color.White,
                            modifier = Modifier
                                .size(20.dp)
                                .zIndex(1f)
                                .constrainAs(arrowBack) {
                                    start.linkTo(circle.start, 8.dp)
                                    top.linkTo(circle.top)
                                    bottom.linkTo(circle.bottom)
                                }
                        )
                    }
                    IconButton(
                        onClick = onChangeCoverProfile,
                        Modifier
                            .clip(CircleShape)
                            .background(color = LightBlack_65)
                            .zIndex(1f)
                            .size(32.dp)

                    ) {
                        Icon(
                            androidx.compose.material.icons.Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.back),
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                EditProfileInformation(
                    backImageProfile = state.profileCover,
                    profileImage = state.profileAvatar,
                    onEdit = onChangeProfileImage
                )
            }
            SpacerVertical32()
            InputTextField(
                modifier = Modifier.padding(top = spacingExtraLarge, bottom = spacingMedium),
                value = state.firstName,
                onValueChange = onChangeFirstName,
                placeholder = stringResource(R.string.first_name),
                icon = androidx.compose.material.icons.Icons.Default.Person,
                action = ImeAction.Default
            )

            InputTextField(
                modifier = Modifier.padding(bottom = spacingMedium),
                value = state.lastName,
                onValueChange = onChangeLastName,
                placeholder = stringResource(R.string.last_name),
                icon = androidx.compose.material.icons.Icons.Default.Person,
                action = ImeAction.Default
            )

            InputTextField(
                modifier = Modifier.padding(bottom = spacingMedium),
                value = state.email,
                onValueChange = onChangeEmail,
                placeholder = stringResource(R.string.email),
                icon = androidx.compose.material.icons.Icons.Default.Email,
                action = ImeAction.Default
            )

            InputTextField(
                modifier = Modifier.padding(bottom = spacingMedium),
                value = state.currentPassword,
                onValueChange = onChangeCurrentPassword,
                placeholder = stringResource(R.string.current_password),
                icon = androidx.compose.material.icons.Icons.Default.Lock,
                action = ImeAction.Default,
                isPassword = !state.showCurrentPassword,
                trailingIcon = {
                    IconButton(onClick = onClickShowCurrentPassword) {
                        if (state.showCurrentPassword) {
                            Icon(
                                androidx.compose.material.icons.Icons.Filled.Visibility,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                androidx.compose.material.icons.Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    }
                }
            )

            InputTextField(
                modifier = Modifier.padding(bottom = spacingMedium),
                value = state.newPassword,
                onValueChange = onChangeNewPassword,
                placeholder = stringResource(R.string.new_password),
                icon = androidx.compose.material.icons.Icons.Default.Lock,
                action = ImeAction.Default,
                isPassword = !state.showNewPassword,
                trailingIcon = {
                    IconButton(onClick = onClickShowNewPassword) {
                        if (state.showNewPassword) {
                            Icon(
                                androidx.compose.material.icons.Icons.Filled.Visibility,
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                androidx.compose.material.icons.Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
            SpacerVertical32()
            CustomButton(
                text = stringResource(R.string.save),
                onClick = onClickSave,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }
    }
}