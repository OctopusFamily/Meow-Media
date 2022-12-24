package com.octopus.socialnetwork.domain.usecase.authentication

import android.util.Log
import com.octopus.socialnetwork.data.repository.authentication.AuthenticationRepository
import com.octopus.socialnetwork.data.repository.firebase.FirebaseRepository
import com.octopus.socialnetwork.domain.mapper.user.toUserFirebaseDTO
import com.octopus.socialnetwork.domain.model.user.UserFirebase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val signInRepository: AuthenticationRepository,
    private val CreateUserAccountInFirebase: FirebaseRepository
) {
    suspend operator fun invoke(params: Params): LoginResponse {

        val response = signInRepository.register(params = params)

        return if (response.code == "100") {
            val userResponse = response.result
            val userInfo = UserFirebase(
                userId = userResponse.userId ?: 0,
                fullName = userResponse.fullname ?: "",
                username = userResponse.username ?: "",
                token = ""
            ).toUserFirebaseDTO()

            CreateUserAccountInFirebase.createUser(userInfo)

            LoginResponse.Success
        } else {
            Log.v("ameer", "error create account  ${response.code} ${response.message}")
            val body = response.result
            LoginResponse.Failure(
                response.message.toString(),
                response.code.toString()
            )
        }
    }

    data class Params(
        var firstName: String,
        var lastName: String,
        var email: String,
        var reEmail: String,
        var gender: String,
        var birthDate: String,
        var userName: String,
        var password: String,
    )

}