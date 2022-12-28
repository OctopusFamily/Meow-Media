package com.octopus.socialnetwork.ui.screen.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.AppBar
import com.octopus.socialnetwork.ui.composable.ImageForEmptyList
import com.octopus.socialnetwork.ui.composable.lotties.LottieError
import com.octopus.socialnetwork.ui.composable.lotties.LottieLoading
import com.octopus.socialnetwork.ui.composable.notifications.ItemNotification
import com.octopus.socialnetwork.ui.screen.home.navigateToHomeScreen
import com.octopus.socialnetwork.ui.screen.notifications.state.NotificationItemsUiState
import com.octopus.socialnetwork.ui.screen.notifications.state.NotificationsUiState
import com.octopus.socialnetwork.ui.screen.notifications.state.NotificationsViewModel
import com.octopus.socialnetwork.ui.theme.DividerColor
import com.octopus.socialnetwork.ui.util.onClickNotification


@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()
    NotificationsContent(
        state = state,
        onClickNotification = { notification ->
            onClickNotification(notification.type, navController, notification)
            viewModel.markViewedNotification(notification)
        },
        onClickBack = { navController.navigateToHomeScreen() },
        onClickTryAgain = viewModel::onClickTryAgain
    )
}


@Composable
private fun NotificationsContent(
    state: NotificationsUiState,
    onClickNotification: (NotificationItemsUiState) -> Unit,
    onClickBack: () -> Unit,
    onClickTryAgain: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {

        AppBar(onClickBack, title = stringResource(R.string.notification))
        Divider(color = DividerColor, thickness = 1.dp)

        if (state.isLoading) {
            LottieLoading()
        } else if (state.isError) {
            LottieError(onClickTryAgain)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.background),
            ) {
                if (state.notifications.isEmpty()) {
                    item { ImageForEmptyList(modifier = Modifier.padding(vertical = 116.dp)) }
                } else {
                    items(state.notifications) { notification ->
                        ItemNotification(notification, onClickNotification)
                    }
                }
            }
        }

    }
}