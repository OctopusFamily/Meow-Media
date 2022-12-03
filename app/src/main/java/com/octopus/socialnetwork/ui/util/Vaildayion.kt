package com.octopus.socialnetwork.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.octopus.socialnetwork.R
import java.util.regex.Pattern

@Composable
fun requiredValidation(text: String): String? {
    return if (text.isEmpty() || text.isBlank()) stringResource(R.string.is_required) else null
}

@Composable
fun passwordShortValidation(text: String): String? {
    return if (text.length < 6) stringResource(R.string.password_short) else null
}

fun emailValidation(email: String): String? {
    if (email.isEmpty() || email.isBlank()) {
        return "Is required"
    }
    return if (Pattern.matches(Constants.REGEX_EMAIL_VALIDATION, email)) null else "Invalid email"
}