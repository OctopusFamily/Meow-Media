package com.octopus.socialnetwork.data.repository.authentication

import com.octopus.socialnetwork.data.remote.response.dto.auth.AuthResponse
import retrofit2.Response

interface AuthenticationRepository {
    suspend fun login(username: String, password: String): Response<AuthResponse>

    suspend fun signup(
        firstName: String,
        lastName: String,
        email: String,
        reEmail: String,
        gender: String,
        birthDate: String,
        userName: String,
        password: String,
    ): Response<AuthResponse>
}