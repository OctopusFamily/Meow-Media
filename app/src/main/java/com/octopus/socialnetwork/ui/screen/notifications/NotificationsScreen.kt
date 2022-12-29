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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.AppBar
import com.octopus.socialnetwork.ui.composable.ImageForEmptyList
import com.octopus.socialnetwork.ui.composable.lotties.LottieError
import com.octopus.socialnetwork.ui.composable.lotties.LottieLoading
import com.octopus.socialnetwork.ui.composable.notifications.ItemNotification
import com.octopus.socialnetwork.ui.screen.comments.navigateToCommentsScreen
import com.octopus.socialnetwork.ui.screen.notifications.state.NotificationItemsUiState
import com.octopus.socialnetwork.ui.screen.notifications.state.NotificationsUiState
import com.octopus.socialnetwork.ui.screen.post.navigateToPostScreen
import com.octopus.socialnetwork.ui.theme.DividerColor
import com.octopus.socialnetwork.ui.util.extensions.notificationsTypes


@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()
    NotificationsContent(
        state = state,
        onClickNotification = { notification ->
            when (notification.type) {
                notificationsTypes.LIKE_POST -> navController.navigateToPostScreen(
                    notification.subjectId,
                    notification.ownerId
                )

                notificationsTypes.LIKE_ANNOTATION_COMMENTS_POST -> navController.navigateToCommentsScreen(
                    notification.subjectId,
                    notification.type
                )


                notificationsTypes.COMMENTS_POST -> {
                    navController.navigateToPostScreen(notification.subjectId, notification.ownerId)
                    navController.navigateToCommentsScreen(
                        notification.subjectId,
                        notification.type
                    )
                }

                else -> navController.navigateToPostScreen(
                    notification.subjectId,
                    notification.ownerId
                )
            }

            viewModel.markViewedNotification(notification)
        },
        onClickBack = { navController.popBackStack() },
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

    val notifications = state.notifications.collectAsLazyPagingItems()
    val isEmptyFlow = notifications.itemSnapshotList.isEmpty()

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
        }
        if (state.isError) {
            LottieError(onClickTryAgain)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.background),
            ) {


                if (isEmptyFlow) {
                    item { ImageForEmptyList(modifier = Modifier.padding(vertical = 116.dp)) }
                }

                items(notifications) { notification ->
                    notification?.let { ItemNotification(it, onClickNotification) }
                }
                when (notifications.loadState.append) {
                    is LoadState.NotLoading -> Unit
                    LoadState.Loading -> {
                        item {  LottieLoading() }
                    }
                    is LoadState.Error -> {
                        item { LottieLoading() }
                    }
                }


            }
        }

    }
}