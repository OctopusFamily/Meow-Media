package com.octopus.socialnetwork.domain.mapper.posts

import com.octopus.socialnetwork.data.remote.response.dto.post.PostDTO
import com.octopus.socialnetwork.domain.model.post.Post

fun PostDTO.asPost(): Post {
    return Post(
        postId = postId ?: 0,
        ownerId = ownerId ?: 0,
        title = title ?: "",
    )
}