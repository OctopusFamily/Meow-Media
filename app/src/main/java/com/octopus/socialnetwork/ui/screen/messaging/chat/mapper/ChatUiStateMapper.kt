package com.octopus.socialnetwork.ui.screen.messaging.chat.mapper

import com.octopus.socialnetwork.domain.model.messages.Messages
import com.octopus.socialnetwork.ui.screen.messaging.chat.state.ChatUiState

import com.octopus.socialnetwork.ui.util.extensions.getHourAndMinutes
import com.octopus.socialnetwork.ui.util.extensions.removeHtmlEncoding

fun Messages.toChatUiState(): ChatUiState {
    return ChatUiState(
        message = message.removeHtmlEncoding(),
        lastSendTime = time.getHourAndMinutes(),
        isSentByMe = isSentByMe
    )
}