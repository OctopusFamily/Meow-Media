package com.octopus.socialnetwork.data.remote.service

import com.octopus.socialnetwork.data.remote.response.dto.auth.AuthResponse
import com.octopus.socialnetwork.data.remote.response.dto.base.BaseResponse
import com.octopus.socialnetwork.data.remote.response.dto.massages.RecentmessagesListDto.RecentMessagesListDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.UserDetailsDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.UserFriendsDTO
import com.octopus.socialnetwork.data.remote.response.dto.user.UserPostsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SocialService {
    @POST("user_authenticate")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Response<AuthResponse>

    @POST("user_add")
    suspend fun signup(
        @Query("firstname") firstName: String,
        @Query("lastname") lastName: String,
        @Query("email") email: String,
        @Query("reemail") reEmail: String,
        @Query("gender") gender: String,
        @Query("birthdate") birthDate: String,
        @Query("username") userName: String,
        @Query("password") password: String,
    ): Response<AuthResponse>

    @GET("user_details")
    suspend fun getUserDetails(
        @Query("guid") visitedUserId: Int,
    ): BaseResponse<UserDetailsDTO>

    @GET("user_friends")
    suspend fun getUserFriends(
        @Query("guid") visitedUserId: Int,
    ): BaseResponse<UserFriendsDTO>

    @GET("wall_list_user")
    suspend fun getUserPosts(
        @Query("uguid") visitedUserId: Int,
        @Query("guid") currentUserId: Int,
    ): BaseResponse<UserPostsDTO>

    @GET("message_recent")
    suspend fun getMassagesListRecent(
        @Query("guid") UserId: Int
    ): BaseResponse<RecentMessagesListDTO>

//    @POST("message_add")
//    suspend fun sendMessage(
//        @Query("from") from: Int,
//        @Query("to") to: Int,
//        @Query("massage") message: String
//    ): Response<>
//
//    @POST("message_new")
//    suspend fun setUnreadMessages(
//        @Query("from") from: Int,
//        @Query("to") to: Int,
//        @Query("markallread") markAllRead: String
//    ): Response<>
//
//    @POST("message_list")
//    suspend fun setMessageList(
//        @Query("guid") userId: Int,
//        @Query("to") to: Int,
//    ): Response<>
//


}
