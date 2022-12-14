package com.octopus.socialnetwork.ui.screen.login.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octopus.socialnetwork.domain.usecase.authentication.login.LoginUseCase
import com.octopus.socialnetwork.domain.usecase.authentication.validation.PasswordValidationUseCase
import com.octopus.socialnetwork.domain.usecase.authentication.validation.UserNameValidationUseCase
import com.octopus.socialnetwork.ui.screen.register.mapper.toPasswordUiState
import com.octopus.socialnetwork.ui.screen.register.mapper.toUserNameUiState
import com.octopus.socialnetwork.ui.screen.register.state.uistate.PasswordState
import com.octopus.socialnetwork.ui.screen.register.state.uistate.UserNameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userNameValidation: UserNameValidationUseCase,
    private val passwordValidation: PasswordValidationUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()



    fun onChangeUsername(newUsername: String) {
        if (newUsername.isNotEmpty()) {
            showErrorValidationInput()
        }
        val username = newUsername.trim()
        val usernameState = userNameValidation(username).toUserNameUiState()
        if (usernameState == UserNameState.VALID) {
            usernameState(username, true)
        } else {
            usernameState(username, false, usernameState.message)
        }

    }

    private fun usernameState(
        username: String,
        isValidInputs: Boolean,
        error: Int? = null
    ) {
        _state.update {
            it.copy(
                isValidInputs = isValidInputs,
                userInput = it.userInput.copy(
                    userName = it.userInput.userName.copy(
                        text = username,
                        error = error,
                        isValid = isValidInputs
                    )
                )
            )
        }
    }

    fun onChangePassword(newPassword: String) {
        val password = newPassword.trim()
        val passwordValidation = passwordValidation(password).toPasswordUiState()
        if (passwordValidation == PasswordState.VALID) {
            passwordState(password, true)
        } else {
            passwordState(password = password, false, passwordValidation.message)
        }
    }

    private fun passwordState(password: String, isValidInputs: Boolean, error: Int? = null) {
        _state.update {
            it.copy(
                isValidInputs = isValidInputs,
                userInput = it.userInput.copy(
                    password = it.userInput.password.copy(
                        text = password, error = error, isValid = isValidInputs
                    )
                )
            )
        }
    }

    fun checkValidateToLogin(){
        val userInput = state.value.userInput
        if (userInput.userName.isValid && userInput.password.isValid) {
            login()
        } else {
            showErrorValidationInput()
        }
    }

    fun login() {
        onLoading()
        val userInput = _state.value.userInput
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginResponse =
                    loginUseCase(userInput.userName.text, userInput.password.text)
                Log.i("TESTING", "$loginResponse is login response")
                if (loginResponse.username.isNullOrEmpty()) {
                    _state.update { it.copy(isError = true, isLoading = false) }
                } else {
                    _state.update { it.copy(isError = false, isLoading = false, isSuccess = true) }
                }
            } catch (e: Exception) {
                Log.i("TESTING","catched $e")
                _state.update {
                    it.copy(
                        isError = true,
                        errorMessage = if (e is java.lang.NullPointerException) "the username or password might be wrong" else e.localizedMessage.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = "") }
    }

    private fun showErrorValidationInput() {
        _state.update { it.copy(isDisplayErrorValidationInputs = true) }
    }

    fun changePasswordVisibility() {
        _state.update { it.copy(showPassword = !it.showPassword) }
    }

    private fun onLoading() {
        _state.update { it.copy(isLoading = !_state.value.isLoading) }
    }
}