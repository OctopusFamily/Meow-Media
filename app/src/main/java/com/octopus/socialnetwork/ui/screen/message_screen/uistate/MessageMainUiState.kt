package com.octopus.socialnetwork.ui.screen.message_screen.uistate

data class MessageMainUiState(
    val isFail: Boolean = false,
    val isLoading: Boolean = true,
    var unreadMessagesCount: Int = 0,
    val messages: List<MessageUiState> = emptyList(),
    val message: String = "",
    val senderName: String = "",
    val senderId: Int = 0,
    val avatar: String = "",
    val query: String = "",
)