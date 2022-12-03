package com.octopus.socialnetwork.ui.screen.profile.uistate

data class ProfileUiState(
    val isLoading: Boolean = true,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val fullName: String = "",
    val username: String = "",
    val friendsCount :String = "0" ,
    val postCount: String = "0",
    val profileAvatar: String = "",
    val profileCover: String = "",
    val profilePosts: List<ProfilePostUiState> = emptyList()
)

data class ProfilePostUiState(
    val postId: Int = 0,
    val postImage: String = ""
)
