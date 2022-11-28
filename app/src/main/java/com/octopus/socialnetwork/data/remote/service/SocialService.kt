package com.octopus.socialnetwork.data.remote.service

import com.octopus.socialnetwork.data.remote.dto.AuthResponse
import retrofit2.http.POST
import retrofit2.http.Query


interface SocialService {

    //login
    @POST("user_authenticate")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): AuthResponse

    //signup
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
    ): AuthResponse
  }
