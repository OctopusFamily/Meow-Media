package com.octopus.socialnetwork.data.repository.authentication

import com.octopus.socialnetwork.data.remote.response.base.BaseResponse
import com.octopus.socialnetwork.data.remote.response.dto.auth.AuthResponse
import com.octopus.socialnetwork.data.remote.response.dto.auth.RegisterDto
import com.octopus.socialnetwork.domain.usecase.authentication.register.RegisterUseCase
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun login(username: String, password: String): AuthResponse

    suspend fun register(params: RegisterUseCase.Params): BaseResponse<RegisterDto>

    fun getUserId(): Flow<Int?>

    suspend fun getLocalFcmToken(): String?
    suspend fun deleteUserId()
}