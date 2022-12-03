package com.octopus.socialnetwork.data.repository.social

import com.octopus.socialnetwork.data.remote.response.dto.album.AlbumsDto
import com.octopus.socialnetwork.data.remote.response.dto.album.album_photos_list.AlbumPhotosDTO
import com.octopus.socialnetwork.data.remote.response.dto.base.BaseResponse
import com.octopus.socialnetwork.data.remote.response.dto.like.LikeDTO
import com.octopus.socialnetwork.data.remote.response.dto.post.PostDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.CheckUserFriendDTO
import com.octopus.socialnetwork.data.remote.response.dto.notifications.NotificationItemsDTO
import com.octopus.socialnetwork.data.remote.response.dto.notifications.UserNotificationsCountDTO
import com.octopus.socialnetwork.data.remote.response.dto.notifications.UserNotificationsDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.UserDetailsDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.UserFriendsDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.UserPostsDTO
import com.octopus.socialnetwork.data.remote.service.SocialService
import javax.inject.Inject

class SocialRepositoryImpl @Inject constructor(
    private val socialService: SocialService,
) : SocialRepository {

    override suspend fun getUserDetails(visitedUserId: Int): UserDetailsDTO {
        return socialService.getUserDetails(visitedUserId).result
    }

    override suspend fun getUserFriends(visitedUserId: Int): UserFriendsDTO {
        return socialService.getUserFriends(visitedUserId).result
    }

    override suspend fun getUserPosts(visitedUserId: Int, currentUserId: Int): UserPostsDTO {
        return socialService.getUserPosts(visitedUserId, currentUserId).result
    }


    override suspend fun viewPost(postId: Int, userId: Int): BaseResponse<PostDTO> {
        return socialService.viewPost(
            postId,
            userId,
        )
    }

    override suspend fun viewUserPosts(ownerId: Int, viewerId: Int): List<BaseResponse<PostDTO>> {
        return socialService.viewUserPosts(
            ownerId,
            viewerId,
        )
    }

    override suspend fun viewNewsFeed(userId: Int): List<BaseResponse<PostDTO>> {
        return socialService.viewNewsFeed(
            userId,
        )
    }

    override suspend fun createPost(): BaseResponse<PostDTO> {
        return socialService.createPost()
    }

    override suspend fun deletePost(postId: Int, userId: Int): BaseResponse<PostDTO> {
        return socialService.deletePost(
            postId,
            userId,
        )
    }

    override suspend fun like(
        currentUserId: Int,
        contentId: Int,
        typeContent: String
    ): BaseResponse<LikeDTO> {
        return socialService.like(currentUserId, contentId, typeContent)
    }

    override suspend fun unlike(
        currentUserId: Int,
        contentId: Int,
        typeContent: String
    ): BaseResponse<LikeDTO> {
        return socialService.unlike(currentUserId, contentId, typeContent)
    }

    override suspend fun checkUserFriend(
        currentUserId: Int,
        userIdWantedToCheck: Int
    ): BaseResponse<CheckUserFriendDTO> {
        return socialService.checkUserFriend(currentUserId, userIdWantedToCheck)
    }




    override suspend fun getAlbumsUser(ownerAlbumsUserId: Int, visitedUserId: Int): AlbumsDto {
        return socialService.getAlbumsUser(ownerAlbumsUserId, visitedUserId).result
    }

    override suspend fun getAlbumPhotos(albumId: Int): AlbumPhotosDTO {
        return socialService.getAlbumPhotos(albumId).result
    }

    override suspend fun createAlbum(
        title: String,
        currentUserId: Int,
        privacy: Int
    ): Int {
        return socialService.createAlbum(title, currentUserId, privacy).result.albumId ?: 0
    }

    override suspend fun deleteAlbumPhoto(
        photoid: Int,
        visitedUserId: Int
    ): Boolean {
        return socialService.deleteAlbumPhoto(photoid, visitedUserId).result.status ?: false
    }

//----------------------------------- Notifications -----------------------------------//
    override suspend fun getUserNotifications(currentUserId: Int, types: String?, offset:Int?): UserNotificationsDTO {
        return socialService.getUserNotifications(currentUserId, types ?: "", offset ?: 1).result
    }

    override suspend fun getUserNotificationsCount(currentUserId: Int, types: String?): UserNotificationsCountDTO {
        return socialService.getUserNotificationsCount(currentUserId, types ?: "").result
    }

    override suspend fun markUserNotificationsAsViewed(notificationId: Int): NotificationItemsDTO {
        return socialService.markUserNotificationsAsViewed(notificationId).result
    }

}