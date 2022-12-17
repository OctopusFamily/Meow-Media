package com.octopus.socialnetwork.ui.screen.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.ProfileImage
import com.octopus.socialnetwork.ui.composable.underLineBoarder
import com.octopus.socialnetwork.ui.screen.chat.uistate.MessageUiState
import com.octopus.socialnetwork.ui.theme.outLine
import com.octopus.socialnetwork.ui.theme.textPrimaryColor

@Composable
fun ChatScreenTopBar(
    state: MessageUiState,
    onClickBack: () -> Unit,
    onClickImage: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .underLineBoarder(color = MaterialTheme.colors.outLine, strokeWidth = 1.dp)
            .height(56.dp)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        IconButton(onClick = onClickBack) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                contentDescription = stringResource(id = R.string.icon_arrow_back),
                tint =  MaterialTheme.colors.textPrimaryColor,
                modifier = Modifier.size(18.dp)
            )
        }
        Row(
            Modifier.fillMaxWidth().clickable(onClick = {onClickImage(state.otherUser.userId)}),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(
             painter = rememberAsyncImagePainter(model = state.otherUser.profileAvatar),
                modifier = Modifier.size(40.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = state.otherUser.fullName ,
                    color = MaterialTheme.colors.textPrimaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

        }
    }
}