package com.octopus.socialnetwork.ui.composable.comment

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.Avatar
import com.octopus.socialnetwork.ui.screen.comments.uistate.CommentDetailsUiState
import com.octopus.socialnetwork.ui.theme.light_outline
import com.octopus.socialnetwork.ui.util.extensions.getHourAndMinutes


@Composable
fun ItemComment(
    commentDetails: CommentDetailsUiState,
    modifier: Modifier = Modifier,
    onLike: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 8.dp, 8.dp, 16.dp)
    ) {

        val (userImage, fullName, userName, postText, like, likeCounter, reply, contentTime) = createRefs()


        Avatar(
            modifier = Modifier
                .clip(CircleShape)
                .constrainAs(userImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            painter =
            rememberAsyncImagePainter(model = commentDetails.userAvatar), size = 50
        )

        Text(
            text = commentDetails.fullName,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(fullName) {
                top.linkTo(userImage.top, 4.dp)
                start.linkTo(userImage.end, 8.dp)
            })
        Text(
            text = commentDetails.userName,
            fontSize = 12.sp,
            color = light_outline,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.constrainAs(userName) {
                top.linkTo(fullName.bottom, 4.dp)
                start.linkTo(fullName.start)
            })

        Text(text = commentDetails.comment,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .constrainAs(postText) {
                    top.linkTo(userImage.bottom, 16.dp)
                    start.linkTo(userImage.start)
                })

        IconButton(onClick =  onLike,
            modifier = Modifier
                .width(12.dp)
                .height(12.dp)
                .constrainAs(like) {
                    top.linkTo(postText.bottom, 14.dp)
                    start.linkTo(postText.start)
                }) {
            Icon(
                painterResource(
                    if (commentDetails.isLikedByUser) R.drawable.ic_like_12
                    else R.drawable.ic_un_like_12
                ),
                contentDescription = stringResource(id = R.string.like),
                tint = if (commentDetails.isLikedByUser) MaterialTheme.colors.primary else Color.Gray
            )
        }

        Text(text = commentDetails.likeCounter.toString(),
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .alpha(.3f)
                .constrainAs(likeCounter) {
                    start.linkTo(like.end, 4.dp)
                    top.linkTo(like.top, 4.dp)
                    bottom.linkTo(like.bottom)
                })

        Text(text = commentDetails.timeCreated.getHourAndMinutes(),
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.alpha(.3f).constrainAs(contentTime) {
                    top.linkTo(postText.bottom, 12.dp)
                    start.linkTo(likeCounter.end, 18.dp)
                })

    }
}