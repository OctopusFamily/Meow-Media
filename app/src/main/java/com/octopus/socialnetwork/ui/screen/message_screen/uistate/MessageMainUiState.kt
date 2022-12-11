package com.octopus.socialnetwork.ui.screen.message_screen.uistate

data class MessageMainUiState(
    val isFail: Boolean = false,
    val isLoading: Boolean = true,
    val messages: MessageUiState = MessageUiState(),
    var unreadMessagesCount: Int = 0,
    val recentMessages: List<MessageUiState> = emptyList(),
)