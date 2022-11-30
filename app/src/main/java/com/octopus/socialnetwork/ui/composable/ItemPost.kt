package com.octopus.socialnetwork.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.octopus.socialnetwork.ui.composable.home.SmallPostDetails
import com.octopus.socialnetwork.ui.composable.post.PostAction
import com.octopus.socialnetwork.ui.composable.post.PostImage
import com.octopus.socialnetwork.ui.screen.post.PostUiState
import com.octopus.socialnetwork.ui.theme.Transparent

@Composable
fun ItemPost(
    post: PostUiState,
    like: () -> Unit,
    comment: () -> Unit,
    share: () -> Unit
) {

    Box(
        modifier = Modifier
            .height(380.dp)
            .clip(shape = RoundedCornerShape(16.dp))
    ) {

        PostImage(postImage = rememberAsyncImagePainter(model = post.postImage))

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {

            Card(
                modifier = Modifier
                    .height(210.dp)
                    .width(48.dp),
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                backgroundColor = Transparent,
            ) {
                PostAction(
                    likeCount = post.likeCount,
                    commentCount = post.commentCount,
                    like = like,
                    comment = comment,
                    share = share,
                    modifier = Modifier.size(18.dp)
                )
            }
        }



        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(alignment = Alignment.BottomCenter),
            elevation = 0.dp,
            shape = AbsoluteRoundedCornerShape(bottomLeft = 16.dp, bottomRight = 16.dp),
            backgroundColor = Color.Transparent

        ) {
            SmallPostDetails(post = post)

        }

    }
}

