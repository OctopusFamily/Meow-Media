package com.octopus.socialnetwork.ui.composable.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.octopus.socialnetwork.ui.composable.friend_requests.UserDetailsItem
import com.octopus.socialnetwork.ui.screen.profile.state.UserDetailsUiState
import com.octopus.socialnetwork.ui.theme.spacingExtraLarge
import com.octopus.socialnetwork.ui.theme.spacingXSmall

@Composable
fun Friends(
    state: List<UserDetailsUiState>,
    onClickItem: (Int) -> Unit,
) {
    LazyColumn(
        Modifier.padding(top = spacingXSmall, bottom = spacingExtraLarge)
    ) {
        items(state) { user ->
            UserDetailsItem(state = user, onClickItem = onClickItem)
        }
    }
}