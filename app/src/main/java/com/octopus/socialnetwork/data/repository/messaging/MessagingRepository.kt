package com.octopus.socialnetwork.data.repository.messaging

import com.octopus.socialnetwork.data.remote.response.dto.messages.list_messages.MessageListDTO
import com.octopus.socialnetwork.data.remote.response.dto.messages.message_send.SendMessageDTO
import com.octopus.socialnetwork.data.remote.response.dto.messages.recent_messages.RecentMessagesDTO
import com.octopus.socialnetwork.data.remote.response.dto.messages.unread_message.UnreadMessagesDTO

interface MessagingRepository {
    suspend fun getRecentMassagesList(messageReceiver: Int): RecentMessagesDTO

    suspend fun sendMessage(
        messageSenderId: Int,
        messageReceiverId: Int,
        message: String,
    ): SendMessageDTO

    suspend fun unreadMessages(
        messageSenderId: Int,
        messageReceiverId: Int,
        message: String,
    ): UnreadMessagesDTO

    suspend fun messageList(messageSenderId: Int, messageReceiverId: Int): MessageListDTO
}