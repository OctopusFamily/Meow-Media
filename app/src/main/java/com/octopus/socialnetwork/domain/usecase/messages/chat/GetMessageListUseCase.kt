package com.octopus.socialnetwork.domain.usecase.messages.chat

import com.octopus.socialnetwork.data.repository.messaging.MessagingRepository
import com.octopus.socialnetwork.domain.mapper.messages.toMessageDetails
import com.octopus.socialnetwork.domain.model.messages.MessageDetails
import com.octopus.socialnetwork.domain.usecase.user.FetchUserIdUseCase
import javax.inject.Inject

class GetMessageListUseCase @Inject constructor(
    private val messagingRepository: MessagingRepository,
    private val fetchUserIdUseCase: FetchUserIdUseCase,
) {
    suspend operator fun invoke(otherUserId: Int): List<MessageDetails> {
        val userId = fetchUserIdUseCase()
        return messagingRepository.getMessages(
            userId,
            otherUserId
        ).messages?.map { it.toMessageDetails(userId) } ?: emptyList()
    }
}