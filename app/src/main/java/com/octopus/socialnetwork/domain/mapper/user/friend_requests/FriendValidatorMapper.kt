package com.octopus.socialnetwork.domain.mapper.user.friend_requests

import com.octopus.socialnetwork.data.remote.response.dto.user.FriendValidatorDTO
import com.octopus.socialnetwork.domain.model.user.FriendValidator

fun FriendValidatorDTO.toCheckUserFriend(): FriendValidator {
    return FriendValidator(
        isFriend = isFriend ?: false,
        requestExists = requestExists ?: false,
        success = success ?: false,
    )
}