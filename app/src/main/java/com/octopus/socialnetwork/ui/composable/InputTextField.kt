package com.octopus.socialnetwork.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.octopus.socialnetwork.ui.theme.*

@Composable
fun InputTextField(
    placeholder: String,
    icon: ImageVector,
    value: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isReadOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable() (() -> Unit)? = null,
    action: ImeAction
) {
    OutlinedTextField(
        modifier = modifier.height(heightDefaultButton).fillMaxWidth().padding(horizontal = spacingMedium),
        shape = Shapes.large,
        value = value,
        readOnly = isReadOnly,
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(imeAction = action),
        placeholder = {
            Text(
                text = placeholder, style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.textThirdColor
            )
        },
        leadingIcon = {
            Icon(
                icon,
                contentDescription = "$placeholder IconDTO",
                tint = Color.Gray,
            )
        },
        trailingIcon = trailingIcon,
        textStyle = MaterialTheme.typography.h6,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = MaterialTheme.colors.primary,
            focusedBorderColor = MaterialTheme.colors.textPrimaryColor,
            unfocusedBorderColor =  Color.Gray,
            focusedLabelColor = MaterialTheme.colors.primary,
            errorBorderColor = MaterialTheme.colors.error,
            textColor = MaterialTheme.colors.textPrimaryColor
        ),
    )
}