package com.octopus.socialnetwork.domain.mapper.posts

import com.octopus.socialnetwork.data.remote.response.dto.post.PostDto
import com.octopus.socialnetwork.domain.model.post.Post

fun PostDto.asPost(): Post {
    return Post(
        postId = details?.postId ?: 0,
        ownerId = details?.ownerId ?: 0,
        description = description ?: "",
        image = image ?: "",
        fullName = posted_user?.fullName ?: "",
        username = posted_user?.username ?: "",
        avatar = posted_user?.avatar?.larger ?: "",
        totalLikes = details?.totalLikes ?: 0,
        totalComments = details?.totalComments ?: 0,
        isLikedByUser = details?.isLikedByUser ?: false,
        timeCreated = details?.timeCreated ?: ""
    )
}

