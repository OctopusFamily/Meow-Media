package com.octopus.socialnetwork.data.repository.social

import com.octopus.socialnetwork.data.remote.response.dto.album.AlbumsDto
import com.octopus.socialnetwork.data.remote.response.dto.album.album_photos_list.AlbumPhotosDTO
import com.octopus.socialnetwork.data.remote.response.dto.base.BaseResponse
import com.octopus.socialnetwork.data.remote.response.dto.like.LikeDTO
import com.octopus.socialnetwork.data.remote.response.dto.notifications.NotificationItemsDTO
import com.octopus.socialnetwork.data.remote.response.dto.notifications.UserNotificationsCountDTO
import com.octopus.socialnetwork.data.remote.response.dto.notifications.UserNotificationsDTO
import com.octopus.socialnetwork.data.remote.response.dto.post.PostDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.CheckUserFriendDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.UserDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.UserFriendsDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.UserPostsDTO
import com.octopus.socialnetwork.data.remote.service.SocialService
import javax.inject.Inject

class SocialRepositoryImpl @Inject constructor(
    private val socialService: SocialService,
) : SocialRepository {

    //region user
    override suspend fun getUserDetails(visitedUserId: Int): UserDTO {
        return socialService.getUserDetails(visitedUserId).result
    }

    override suspend fun getUserFriends(visitedUserId: Int): UserFriendsDTO {
        return socialService.getUserFriends(visitedUserId).result
    }

    override suspend fun checkUserFriend(
        currentUserId: Int,
        userIdWantedToCheck: Int
    ): BaseResponse<CheckUserFriendDTO> {
        return socialService.checkUserFriend(currentUserId, userIdWantedToCheck)
    }

    override suspend fun getUserPosts(visitedUserId: Int, currentUserId: Int): UserPostsDTO {
        return socialService.getUserPosts(visitedUserId, currentUserId).result
    }

    //endregion
    //region post
    override suspend fun viewPost(postId: Int, userId: Int): BaseResponse<PostDTO> {
        return socialService.viewPost(
            postId,
            userId,
        )
    }

    override suspend fun viewUserPosts(
        visitedUserId: Int,
        currentUserId: Int
    ): List<BaseResponse<PostDTO>> {
        return socialService.viewUserPosts(
            visitedUserId,
            currentUserId,
        )
    }

    override suspend fun viewNewsFeed(userId: Int): List<BaseResponse<PostDTO>> {
        return socialService.viewNewsFeed(
            userId,
        )
    }

//    override suspend fun createPost(): BaseResponse<PostDTO> {
////        return socialService.createPost()
//    }

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
//endregion


    //region albums
    override suspend fun getAlbumsUser(albumOwnerUserId: Int, viewerUserId: Int): AlbumsDto {
        return socialService.getAlbumsUser(albumOwnerUserId, viewerUserId).result
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
        photoId: Int,
        visitedUserId: Int
    ): Boolean {
        return socialService.deleteAlbumPhoto(photoId, visitedUserId).result.status ?: false
    }
//endregion

    //region notifications
    override suspend fun getUserNotifications(
        currentUserId: Int,
        types: String?,
        offset: Int?
    ): UserNotificationsDTO {
        return socialService.getUserNotifications(currentUserId, types ?: "", offset ?: 1).result
    }

    override suspend fun getUserNotificationsCount(
        currentUserId: Int,
        types: String?
    ): UserNotificationsCountDTO {
        return socialService.getUserNotificationsCount(currentUserId, types ?: "").result
    }

    override suspend fun markUserNotificationsAsViewed(notificationId: Int): NotificationItemsDTO {
        return socialService.markUserNotificationsAsViewed(notificationId).result
    }
//endregion
}