package com.octopus.socialnetwork.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octopus.socialnetwork.domain.usecase.authentication.LoginUseCase
import com.octopus.socialnetwork.domain.usecase.authentication.validation.PasswordValidationUseCase
import com.octopus.socialnetwork.domain.usecase.authentication.validation.UserNameOrEmailValidationUseCase
import com.octopus.socialnetwork.ui.screen.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userNameOrEmailValidation: UserNameOrEmailValidationUseCase,
    private val passwordValidation: PasswordValidationUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()


    fun onChangeUsernameOrEmail(newUsernameOrEmail: String) {
        _state.update {
            it.copy(

                userInput = it.userInput.copy(
                    userNameOrEmail = it.userInput.userNameOrEmail.copy(
                        text = newUsernameOrEmail
                    )
                )
            )
        }
    }

    fun onChangePassword(newPassword: String) {
        _state.update {
            it.copy(
                userInput = it.userInput.copy(
                    password = it.userInput.password.copy(
                        text = newPassword
                    )
                )
            )
        }
    }

    fun login() {
        onLoading()
        val userInput = _state.value.userInput
        viewModelScope.launch {
            try {
                val loginResponse =
                    loginUseCase(userInput.userNameOrEmail.text, userInput.password.text)
                if (loginResponse?.username.isNullOrEmpty()) {
                    _state.update { it.copy(isError = true, isLoading = false) }
                } else {
                    _state.update { it.copy(isError = false, isLoading = false, isSuccess = true) }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isError = true,
                        errorMessage = e.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun changePasswordVisibility() {
        _state.update { it.copy(showPassword = !it.showPassword) }
    }

    private fun onLoading() {
        _state.update { it.copy(isLoading = !_state.value.isLoading) }
    }
}