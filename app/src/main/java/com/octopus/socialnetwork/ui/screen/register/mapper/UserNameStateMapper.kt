package com.octopus.socialnetwork.ui.screen.register.mapper

import com.octopus.socialnetwork.domain.enums.InputFieldValidation
import com.octopus.socialnetwork.ui.screen.register.uistate.UserNameState


fun InputFieldValidation.toUserNameUiState(): UserNameState {
    return when (this) {
        InputFieldValidation.EMPTY -> UserNameState.EMPTY
        InputFieldValidation.SHORT -> UserNameState.SHORT
        InputFieldValidation.LONG -> UserNameState.LONG
        InputFieldValidation.INVALID -> UserNameState.INVALID
        InputFieldValidation.VALID -> UserNameState.VALID
        else -> UserNameState.EMPTY
    }
}