package com.octopus.socialnetwork.data.repository.social

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.octopus.socialnetwork.BuildConfig
import com.octopus.socialnetwork.data.local.dao.UserDao
import com.octopus.socialnetwork.data.local.dao.PostsDao
import com.octopus.socialnetwork.data.local.database.SocialDatabase
import com.octopus.socialnetwork.data.local.entity.PostEntity
import com.octopus.socialnetwork.data.local.entity.UserEntity
import com.octopus.socialnetwork.data.mapper.toUserEntity
import com.octopus.socialnetwork.data.paging.NotificationDataSource
import com.octopus.socialnetwork.data.paging.PostDataSource
import com.octopus.socialnetwork.data.paging.PostsRemoteMediator
import com.octopus.socialnetwork.data.remote.response.base.BaseResponse
import com.octopus.socialnetwork.data.remote.response.dto.comment.CommentAddDto
import com.octopus.socialnetwork.data.remote.response.dto.comment.CommentDto
import com.octopus.socialnetwork.data.remote.response.dto.comment.CommentEditResponse
import com.octopus.socialnetwork.data.remote.response.dto.like.LikeResponse
import com.octopus.socialnetwork.data.remote.response.dto.notifications.NotificationItemsDto
import com.octopus.socialnetwork.data.remote.response.dto.notifications.NotificationsCountDto
import com.octopus.socialnetwork.data.remote.response.dto.notifications.NotificationsResponse
import com.octopus.socialnetwork.data.remote.response.dto.photo.PhotoDto
import com.octopus.socialnetwork.data.remote.response.dto.photo.ProfilePhotoResponse
import com.octopus.socialnetwork.data.remote.response.dto.photo.UserProfileDto
import com.octopus.socialnetwork.data.remote.response.dto.post.PostDto
import com.octopus.socialnetwork.data.remote.response.dto.post.PostResponse
import com.octopus.socialnetwork.data.remote.response.dto.search.SearchDto
import com.octopus.socialnetwork.data.remote.response.dto.user.FriendsDto
import com.octopus.socialnetwork.data.remote.response.dto.user.PostsDto
import com.octopus.socialnetwork.data.remote.response.dto.user.UserDto
import com.octopus.socialnetwork.data.remote.response.dto.user.friend_requests.FriendRequestsResponse
import com.octopus.socialnetwork.data.remote.response.dto.user.friend_requests.FriendValidatorResponse
import com.octopus.socialnetwork.data.remote.service.apiService.SocialService
import com.octopus.socialnetwork.data.utils.Constants.API_KEY_TOKEN
import com.octopus.socialnetwork.data.utils.Constants.GUID
import com.octopus.socialnetwork.data.utils.Constants.JPEG
import com.octopus.socialnetwork.data.utils.Constants.JPG
import com.octopus.socialnetwork.data.utils.Constants.OSSN_PHOTO
import com.octopus.socialnetwork.data.utils.Constants.OWNER_GUID
import com.octopus.socialnetwork.data.utils.Constants.POST
import com.octopus.socialnetwork.data.utils.Constants.POSTER_GUID
import com.octopus.socialnetwork.data.utils.Constants.PRIVACY
import com.octopus.socialnetwork.data.utils.Constants.PUBLIC_PRIVACY
import com.octopus.socialnetwork.data.utils.Constants.TYPE
import com.octopus.socialnetwork.data.utils.Constants.USER_PHOTO
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@ExperimentalPagingApi
class SocialRepositoryImpl @Inject constructor(
    private val socialService: SocialService,
    private val socialDatabase: SocialDatabase,
    private val postsRemoteMediator: PostsRemoteMediator,
    private val userDao: UserDao,
    private val notificationDataSource: NotificationDataSource,
    private val postDataSource: PostDataSource,
    private val postsDao: PostsDao,
) : SocialRepository {

    //region user
    override suspend fun getUserDetails(visitedUserId: Int): UserDto {
        return socialService.getUserDetails(visitedUserId).result
    }

    override suspend fun insertProfileDetails(userId: Int) {
        return userDao.insertProfileDetails(getUserDetails(userId).toUserEntity())
    }

    override suspend fun getMyProfileDetails(): Flow<UserEntity> {
        return userDao.getProfileDetails()
    }

    override suspend fun getFriends(visitedUserId: Int): FriendsDto {
        return socialService.getUserFriends(visitedUserId).result
    }

    override suspend fun checkUserFriend(myUserId: Int, userIdWantedToCheck: Int): FriendValidatorResponse? {
        return socialService.checkUserFriend(myUserId, userIdWantedToCheck).result
    }

    override suspend fun getPostsCount(visitedUserId: Int, myUserId: Int): PostsDto {
        return socialService.getUserPosts(visitedUserId, myUserId,1).result
    }

    override suspend fun getUserPostsPager(visitedUserId: Int): Pager<Int, PostDto> {
        val dataSource = postDataSource
        dataSource.setUserID(visitedUserId)
        Log.e("TESTING","IN REPOSETORY $dataSource")
        return Pager(
            config = PagingConfig(5,
                prefetchDistance = 5,enablePlaceholders = true) ,
            pagingSourceFactory = { dataSource })
    }

    override suspend fun editUser(myUserId: Int, firstName: String, lastName: String, email: String,
        currentPassword: String, newPassword: String): UserDto {
        return socialService.editUser(myUserId, firstName, lastName, email, currentPassword, newPassword
        ).result
    }

    override suspend fun addFriend(myUserId: Int, userIdWantedToAdd: Int): FriendValidatorResponse {
        return socialService.addFriend(myUserId, userIdWantedToAdd).result
    }

    override suspend fun removeFriend(myUserId: Int, userIdWantedToAdd: Int): FriendValidatorResponse {
        return socialService.removeFriend(myUserId, userIdWantedToAdd).result
    }

    override suspend fun getFriendRequests(myUserId: Int): FriendRequestsResponse {
        return socialService.getFriendRequests(myUserId).result
    }

    //endregion
    //region post
    override suspend fun viewPost(postId: Int, myUserId: Int): PostDto {
        return socialService.viewPost(postId, myUserId).result
    }

    override fun getPostDetails(postId: Int): PostEntity {
        return socialDatabase.postsDao().getPostDetails(postId)
    }

    override suspend fun viewUserPosts(visitedUserId: Int, myUserId: Int): BaseResponse<PostResponse> {
        return socialService.viewUserPosts(visitedUserId, myUserId,)
    }

    override suspend fun createPost(myUserId: Int, posterOwnerId: Int, post: String, type: String,
        photo: File): PostDto {
        val photoExtension = if (photo.extension== JPG ) JPEG else photo.extension
        val requestFile = photo.asRequestBody("image/$photoExtension".toMediaType())
        val builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val requestBody = builder.addFormDataPart(API_KEY_TOKEN, BuildConfig.API_KEY)
            .addFormDataPart(OWNER_GUID, myUserId.toString())
            .addFormDataPart(POSTER_GUID,posterOwnerId.toString())
            .addFormDataPart(TYPE,type)
            .addFormDataPart(POST,post)
            .addFormDataPart(PRIVACY,PUBLIC_PRIVACY)
            .addFormDataPart(OSSN_PHOTO,photo.name,requestFile).build()
        return socialService.createPost(requestBody).result
    }

    override fun getNewsFeedPager(): Flow<PagingData<PostEntity>> {
        return Pager(
            config = PagingConfig(10, prefetchDistance = 5),
            remoteMediator = postsRemoteMediator,
            pagingSourceFactory = { socialDatabase.postsDao().getAllPosts() }
        ).flow
    }

    override suspend fun deletePost(postId: Int, postOwnerId: Int): PostDto {
        return socialService.deletePost(postId, postOwnerId).result
    }

    override suspend fun like(myUserId: Int, contentId: Int, typeContent: String): LikeResponse {
        return socialService.like(myUserId, contentId, typeContent).result
    }

    override suspend fun unlike(myUserId: Int, contentId: Int, typeContent: String): LikeResponse {
        return socialService.unlike(myUserId, contentId, typeContent).result
    }

    override suspend fun getNotifications(myUserId: Int): NotificationsResponse {
        return socialService.getUserNotifications(myUserId = myUserId, page = 1).result
    }

    override suspend fun getNotificationsPager(myUserId: Int): Pager<Int, NotificationItemsDto> {
        val dataSource = notificationDataSource
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { dataSource })
    }


    override suspend fun getNotificationsCount(myUserId: Int): NotificationsCountDto {
        return socialService.getUserNotificationsCount(myUserId).result
    }

    override suspend fun markUserNotificationsAsViewed(notificationId: Int): NotificationItemsDto {
        return socialService.markUserNotificationsAsViewed(notificationId).result
    }

    override suspend fun getCommentsPager(myUserId:Int,type: String,postId: Int, page: Int): List<CommentDto>? {
        return socialService.getCommentsList(myUserId,postId,type,page).result?.comments
    }


    override suspend fun editComment(commentId: Int, comment: String): CommentEditResponse {
        return socialService.editComment(commentId, comment).result
    }

    override suspend fun deleteComment(commentId: Int, userId: Int): Boolean {
        return socialService.deleteComment(commentId, userId).result
    }


    override suspend fun getPhoto(photoId: Int, userId: Int): PhotoDto {
        return socialService.getPhoto(photoId, userId).result
    }

    override suspend fun addComment(postId: Int, comment: String, userId: Int): CommentAddDto {
        return socialService.addComment(postId, comment, userId).result
    }

    override suspend fun updatePostLikeStatusLocally(id: Int, isLikedByUser: Boolean, newLikesCount: Int) {
        socialDatabase.postsDao().updatePostLikeStatus(id, isLikedByUser, newLikesCount)
    }

    override suspend fun getPhotosListProfileCover(userId: Int, type: String
    ): BaseResponse<List<PhotoDto>> {
        return socialService.getPhotosListProfileCover(userId, type)
    }

    override suspend fun getPhotoViewProfile(photoId: Int, userId: Int
    ): BaseResponse<UserProfileDto> {
        return socialService.getPhotoViewProfile(photoId, userId)
    }

    override suspend fun deletePhotoProfile(photoId: Int, userId: Int
    ): BaseResponse<ProfilePhotoResponse> {
        return socialService.deleteCoverPhoto(photoId, userId)
    }

    override suspend fun deleteProfileCover(photoId: Int, userId: Int
    ): BaseResponse<ProfilePhotoResponse> {
        return socialService.deleteCoverPhoto(photoId, userId)
    }

    override suspend fun addProfilePicture(userID: Int, photo: File): UserDto {
        val photoExtension = if (photo.extension==JPG) JPEG else photo.extension
        val requestFile = photo.asRequestBody("image/$photoExtension".toMediaType())
        val builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val requestBody = builder.addFormDataPart(API_KEY_TOKEN, BuildConfig.API_KEY)
            .addFormDataPart(GUID, userID.toString())
            .addFormDataPart(USER_PHOTO,photo.name,requestFile).build()
        return socialService.addProfilePicture(requestBody).result
    }

    override suspend fun addCoverPicture(userID: Int, photo: File): UserDto {
        val photoExtension = if (photo.extension==JPG) JPEG else photo.extension
        val requestFile = photo.asRequestBody("image/$photoExtension".toMediaType())
        val builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val requestBody = builder.addFormDataPart(API_KEY_TOKEN, BuildConfig.API_KEY)
            .addFormDataPart(GUID, userID.toString())
            .addFormDataPart(USER_PHOTO,photo.name,requestFile).build()
        return socialService.addCoverPicture(requestBody).result
    }

    override suspend fun search(myUserId: Int, query: String): SearchDto {
        return socialService.search(myUserId, query).result
    }

    override suspend fun insertPosts(posts: List<PostEntity>) {
        postsDao.insertPosts(posts)
    }

    override suspend fun deletePostFromDB(postId: Int) {
        postsDao.deletePost(postId)
    }
    //endregion

}