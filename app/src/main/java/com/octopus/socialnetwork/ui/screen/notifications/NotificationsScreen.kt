package com.octopus.socialnetwork.ui.screen.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.octopus.socialnetwork.ui.composable.notifications.ItemNotification
import com.octopus.socialnetwork.ui.composable.notifications.NotificationAppBar
import com.octopus.socialnetwork.ui.screen.home.navigateToHomeScreen
import com.octopus.socialnetwork.ui.screen.notifications.state.NotificationItemsUiState
import com.octopus.socialnetwork.ui.screen.notifications.state.NotificationsUiState
import com.octopus.socialnetwork.ui.theme.DividerColor
import com.octopus.socialnetwork.ui.util.extensions.setScreenDestinationOnClickNotification


@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()
    NotificationsContent(
        state = state,
        onClickNotification = { notificationItemsUiState ->
            notificationItemsUiState
                .notificationDetails.type
                .setScreenDestinationOnClickNotification(
                navController,
                notificationItemsUiState
            )
        },
        onClickBack = {
            navController.navigateToHomeScreen()
        }
    )
}


@Composable
private fun NotificationsContent(
    state: NotificationsUiState,
    onClickNotification: (NotificationItemsUiState) -> Unit,
    onClickBack: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {

        NotificationAppBar(onClickBack)

        Divider(color = DividerColor, thickness = 1.dp)

        LazyColumn(modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.notifications) { NotificationItemsUiState ->
                ItemNotification(NotificationItemsUiState, onClickNotification)
            }
        }
    }
}