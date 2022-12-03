package com.octopus.socialnetwork.ui.screen.post.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.octopus.socialnetwork.ui.composable.post.LargPostDetails
import com.octopus.socialnetwork.ui.composable.post.PostAction
import com.octopus.socialnetwork.ui.composable.post.PostImage
import com.octopus.socialnetwork.ui.screen.post.uistate.PostUiState
import com.octopus.socialnetwork.ui.screen.post.viewmodel.PostViewModel
import com.octopus.socialnetwork.ui.theme.LightBlack_65


@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    PostContent(
        state = state,
        onLike = viewModel::onClickLike,
        onComment = viewModel::onClickComment,
        onShare = viewModel::onClickShare
    )

}

@Composable
private fun PostContent(
    state: PostUiState,
    onLike: () -> Unit,
    onComment: () -> Unit,
    onShare: () -> Unit,
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        PostImage(postImage = rememberAsyncImagePainter(model = state.postImage))

        Card(
            modifier = Modifier
                .height(300.dp)
                .align(alignment = Alignment.CenterEnd)
                .width(72.dp),
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
            backgroundColor = LightBlack_65,
        ) {
            PostAction(
                likeCount = state.likeCount,
                commentCount = state.commentCount,
                onLike = onLike,
                onComment = onComment,
                onShare = onShare,
                modifier = Modifier
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(alignment = Alignment.BottomCenter)
                .background(color = LightBlack_65)
        ) {
            LargPostDetails(
                profileImage = rememberAsyncImagePainter(model = state.profileAvatar),
                userName = state.userName,
                fullName = state.fullName,
                postDescription = state.postDescription
            )
        }


    }
}

