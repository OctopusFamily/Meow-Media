package com.octopus.socialnetwork.data.repository.messaging

import com.octopus.socialnetwork.data.local.entity.ConversationEntity
import com.octopus.socialnetwork.data.remote.response.dto.messages.MessageDto
import com.octopus.socialnetwork.data.remote.response.dto.messages.MessageResponse
import com.octopus.socialnetwork.data.remote.response.dto.messages.MessageNotificationDto
import com.octopus.socialnetwork.data.remote.response.dto.messages.NotificationData
import kotlinx.coroutines.flow.Flow

interface MessagingRepository {
    suspend fun getRecentMassagesList(messageReceiver: Int): MessageResponse

    suspend fun sendMessage(
        messageSenderId: Int,
        messageReceiverId: Int,
        message: String,
    ): MessageDto

    suspend fun unreadMessages(
        messageSenderId: Int,
        messageReceiverId: Int,
        markAllRead: Int,
    ): MessageResponse

    suspend fun getMessages(myUserId: Int, friendId: Int,page: Int): MessageResponse

    suspend fun postNotification(notification: MessageNotificationDto): Boolean

    fun onReceiveNotification(): Flow<NotificationData>


    suspend fun insertConversations(conversations: List<ConversationEntity>)
    fun getAllConversations() :Flow<List<ConversationEntity>>
    suspend fun updateConversation(friendId:Int, message: String, time:String)

}