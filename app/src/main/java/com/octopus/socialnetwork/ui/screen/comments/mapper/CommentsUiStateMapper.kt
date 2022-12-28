package com.octopus.socialnetwork.ui.screen.comments.mapper

import com.octopus.socialnetwork.domain.model.comment.Comment
import com.octopus.socialnetwork.ui.screen.comments.state.uistate.CommentDetailsUiState
import com.octopus.socialnetwork.ui.util.extensions.getHourAndMinutes
import com.octopus.socialnetwork.ui.util.extensions.removeHtmlEncoding

fun Comment.toCommentDetailsUiState(): CommentDetailsUiState {
    return CommentDetailsUiState(
        comment = comment.removeHtmlEncoding() ,
        commentOwnerId = commentOwnerId,
        isLikedByUser = isLikedByUser,
        timeCreated = timeCreated.getHourAndMinutes(),
        likeCounter = totalLikes,
        fullName = fullName,
        userName = username,
        userAvatar = avatar,
        commentId = commentId,
    )
}