package com.octopus.socialnetwork.ui.screen.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.octopus.socialnetwork.ui.composable.comment.TypingField
import com.octopus.socialnetwork.ui.composable.social_elements.messages.ReceivedMessage
import com.octopus.socialnetwork.ui.composable.social_elements.messages.SentMessage
import com.octopus.socialnetwork.ui.screen.message_screen.uistate.MessageMainUiState
import com.octopus.socialnetwork.ui.util.extensions.lastIndexOrZero

@SuppressLint("SuspiciousIndentation")
@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    ChatScreenContent(
        state = state,
        onTextChange = viewModel::onTextChange,
        onClickBack = { navController.popBackStack() },
        )
}

@Composable
fun ChatScreenContent(
    state: MessageMainUiState,
    onTextChange: (String) -> Unit,
    onClickBack: () -> Unit,
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White,),
        verticalArrangement = Arrangement.SpaceEvenly

    ) {

        ChatScreenTopBar(senderName = state.senderName, profileImage = state.avatar, onClickBack = onClickBack )

        LazyColumn(
            Modifier
                .fillMaxWidth().weight(.1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = listState
        ) {

            items(state.messages) {
                if (it.isSentByMe) {
                    SentMessage(message = it.message)
                } else {
                    ReceivedMessage(message = it.message)

                }
            }
        }


        TypingField(
            modifier = Modifier.fillMaxWidth(),
            value = "Message",
            onChangeTypingComment = onTextChange,
            onClickSend = {},
            listState = listState,
            index = state.messages.lastIndexOrZero(),
        )
    }



}
