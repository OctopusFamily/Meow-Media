package com.octopus.socialnetwork.domain.usecase.post.create_post

import com.octopus.socialnetwork.data.mapper.toPostEntity
import com.octopus.socialnetwork.data.repository.social.SocialRepository
import com.octopus.socialnetwork.domain.mapper.posts.toPost
import com.octopus.socialnetwork.domain.model.post.Post
import com.octopus.socialnetwork.domain.usecase.authentication.FetchUserIdUseCase
import com.octopus.socialnetwork.domain.utils.Constants.POST_TYPE
import java.io.File
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val fetchUserIdUseCase: FetchUserIdUseCase,
    private val socialRepository: SocialRepository,
) {
    suspend operator fun invoke(description: String, photo: File?): Post? {
        val myUserId = fetchUserIdUseCase()
        return photo?.let {

            val postDto = socialRepository.createPost(
                myUserId,
                myUserId,
                description,
                POST_TYPE,
                it
            )
            postDto?.let {
                socialRepository.insertPosts(listOf(it.toPostEntity()))
            }
            postDto?.toPost()
        }
    }


}