package com.octopus.socialnetwork.ui.screen.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octopus.socialnetwork.domain.usecase.authentication.LoginResponse
import com.octopus.socialnetwork.domain.usecase.authentication.RegisterUseCase
import com.octopus.socialnetwork.ui.screen.register.uistate.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterUiState())
    val state = _state.asStateFlow()

    init {
        register()
    }

    fun register() {
        viewModelScope.launch {

            try {
                var re =
                    registerUseCase(
                    "amret",
                    "ertert",
                    "karino27fdgfgdfg81@ceosdsfsdfhub.com",
                    "karino27fdgfgdfg81@ceosdsfsdfhub.com",
                    "male",
                    "01/10/2022",
                    "amusersdfsdfdsfnamesdsd",
                    "12345dfsdfdsf6"
                )

                when (re) {
                    is LoginResponse.Success -> {
                        Log.v("ameer", "Success")
                    }

                    is LoginResponse.Failure -> {
                        Log.v("ameer", "Failure ${re.message}")
                    }
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true
                    )
                }
            }
        }
    }

    fun showError() {
        _state.update {
            it.copy(displayErrors = true)
        }
    }

    fun validInputs(valid: Boolean) {
        _state.update {
            it.copy(isValidInputs = valid)
        }
    }

    fun tryLogin() {}

    fun onChangeUserName(newValue: String) {
        _state.update {
            it.copy(
                userInfoForm = it.userInfoForm.copy(
                    userName = it.userInfoForm.userName.copy(
                        text = newValue
                    )
                )
            )
        }
    }

    fun onChangeLastName(newValue: String) {
        _state.update {
            it.copy(
                userInfoForm = it.userInfoForm.copy(
                    lastName = it.userInfoForm.lastName.copy(
                        text = newValue
                    )

                )
            )
        }
    }

    fun onChangeFirstName(newValue: String) {
        _state.update {
            it.copy(
                userInfoForm = it.userInfoForm.copy(

                    firstName = it.userInfoForm.firstName.copy(
                        text = newValue
                    )

                )
            )
        }
    }

    fun onChangeEmail(newValue: String) {
        _state.update {
            it.copy(
                userInfoForm = it.userInfoForm.copy(
                    email = it.userInfoForm.email.copy(
                        text = newValue
                    )
                )
            )
        }
    }

    fun onChangeReEmail(newValue: String) {
        _state.update {
            it.copy(
                userInfoForm = it.userInfoForm.copy(
                    reEmail = it.userInfoForm.reEmail.copy(
                        text = newValue
                    )

                )
            )
        }
    }

    fun onChangePassword(newValue: String) {
        _state.update {
            it.copy(
                userInfoForm = it.userInfoForm.copy(
                    password = it.userInfoForm.password.copy(
                        text = newValue
                    )

                )
            )
        }
    }

    fun onChangeGender(newValue: String) {
        _state.update {
            it.copy(
                userInfoForm = it.userInfoForm.copy(
                    gender = it.userInfoForm.gender.copy(
                        text = newValue
                    )

                )
            )
        }
    }

    fun onChangeBirthday(newValue: String) {
        _state.update {
            it.copy(
                userInfoForm = it.userInfoForm.copy(
                    birthday = it.userInfoForm.birthday.copy(
                        text = newValue
                    )

                )
            )
        }

    }

}