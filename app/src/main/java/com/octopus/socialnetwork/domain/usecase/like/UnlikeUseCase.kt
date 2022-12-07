package com.octopus.socialnetwork.domain.usecase.like

import com.octopus.socialnetwork.data.repository.social.SocialRepository
import com.octopus.socialnetwork.domain.mapper.like.toLike
import com.octopus.socialnetwork.domain.model.like.Like
import javax.inject.Inject

class UnlikeUseCase @Inject constructor(
    private val socialRepository: SocialRepository
) {
    suspend operator fun invoke(userId: Int, contentId: Int, typeContent: String): Int? {
        return socialRepository.unlike(userId, contentId, typeContent).count
    }
}