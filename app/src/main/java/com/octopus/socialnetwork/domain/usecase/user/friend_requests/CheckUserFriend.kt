package com.octopus.socialnetwork.domain.usecase.user.friend_requests

import com.octopus.socialnetwork.data.repository.social.SocialRepository
import com.octopus.socialnetwork.domain.mapper.user.friend_requests.toCheckUserFriend
import com.octopus.socialnetwork.domain.model.user.FriendValidator
import com.octopus.socialnetwork.domain.usecase.authentication.FetchUserIdUseCase
import javax.inject.Inject

class CheckUserIsFriendUseCase @Inject constructor(
    private val socialRepository: SocialRepository,
    private val fetchUserIdUseCase: FetchUserIdUseCase,
) {
    suspend operator fun invoke(userIdWantedToCheck: Int): FriendValidator {

        return socialRepository.checkUserFriend(
            fetchUserIdUseCase(),
            userIdWantedToCheck
        )?.toCheckUserFriend() ?: throw Throwable("friend request response is null")
    }
}