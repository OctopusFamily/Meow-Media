package com.octopus.socialnetwork.data.repository.messaging

import com.octopus.socialnetwork.data.remote.response.dto.massages.list_messages.MessageListDTO
import com.octopus.socialnetwork.data.remote.response.dto.massages.message_send.SendMessageDTO
import com.octopus.socialnetwork.data.remote.response.dto.massages.recent_messages.RecentMessagesDTO
import com.octopus.socialnetwork.data.remote.response.dto.massages.unread_message.UnreadMessagesDTO
import com.octopus.socialnetwork.data.remote.service.SocialService
import javax.inject.Inject

class MessagingRepositoryImpl @Inject constructor(
    private val service: SocialService,
) : MessagingRepository {

    override suspend fun getRecentMassagesList(visitedUserId: Int): RecentMessagesDTO {
        return service.getMassagesListRecent(visitedUserId).result
    }

    override suspend fun sendMessage(from: Int, to: Int, message: String): SendMessageDTO {
        return service.sendMessage(from, to, message).result
    }

    override suspend fun unreadMessages(
        from: Int,
        to: Int,
        unreadMessage: String
    ): UnreadMessagesDTO {
        return service.unreadMessages(from, to, unreadMessage).result
    }

    override suspend fun messageList(userId: Int, to: Int): MessageListDTO {
        return service.messageList(userId, to).result
    }
}

