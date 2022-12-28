package com.octopus.socialnetwork.ui.screen.profile.mapper

import com.octopus.socialnetwork.domain.model.post.Post
import com.octopus.socialnetwork.domain.model.user.User
import com.octopus.socialnetwork.ui.screen.profile.state.uistate.ProfilePostUiState
import com.octopus.socialnetwork.ui.screen.profile.state.uistate.UserDetailsUiState


fun User.toUserDetailsUiState(): UserDetailsUiState {
    return UserDetailsUiState(
        fullName = fullName,
        username = username,
        profileAvatar = avatar,
        profileCover = coverUrl,
        userId = id
    )
}

fun Post.toProfilePostUiState(): ProfilePostUiState {
    return ProfilePostUiState(
        postId = postId,
        postOwnerId = ownerId,
        postImage = image
    )
}

fun List<Post>.toProfilePostsUiState(): List<ProfilePostUiState> {
    return this.map { it.toProfilePostUiState() }
}