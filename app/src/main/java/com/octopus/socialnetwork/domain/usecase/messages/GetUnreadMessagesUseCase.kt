package com.octopus.socialnetwork.domain.usecase.messages

import com.octopus.socialnetwork.data.repository.messaging.MessagingRepository
import com.octopus.socialnetwork.domain.mapper.messages.toMessagesList
import com.octopus.socialnetwork.domain.model.messages.MessagesList
import javax.inject.Inject

class GetUnreadMessagesUseCase @Inject constructor(
    private val socialRepository: MessagingRepository
) {
    suspend operator fun invoke(from: Int, to: Int, unreadMessage: Int): MessagesList {
        return socialRepository.unreadMessages(from, to, unreadMessage).toMessagesList()
    }
}