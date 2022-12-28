package com.octopus.socialnetwork.ui.screen.post.state.uistate

data class PostMainUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val postDetails: PostUiState = PostUiState(),
)
