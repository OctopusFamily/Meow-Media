package com.octopus.socialnetwork.ui.screen.edit_profile.uistate


data class EditProfileUiState(
    val userId: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val currentPassword: String = "",
    val newPassword: String = "",
    val email: String = "0",
    val profileAvatar: String = "", //File = File(""),
    val profileCover: String = "",
    val gender: String = "",
    val isLoading: Boolean = true,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
)
