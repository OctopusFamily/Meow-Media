package com.octopus.socialnetwork.domain.usecase.authentication.validation

import com.octopus.socialnetwork.domain.enums.InputFieldValidation
import javax.inject.Inject


class UserNameOrEmailValidationUseCase @Inject constructor(
    private val emailValidation: EmailValidationUseCase,
    private val userNameValidation: UserNameValidationUseCase

) {
    operator fun invoke(usernameOrEmail: String): Pair<InputFieldValidation, Boolean> {
        val isEmail = usernameOrEmail.contains("@")
        val validation = if (isEmail) {
            emailValidation(usernameOrEmail)
        } else {
            userNameValidation(usernameOrEmail)
        }
        return Pair(validation, isEmail)
    }


}