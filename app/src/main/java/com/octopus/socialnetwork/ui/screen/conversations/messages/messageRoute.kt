package com.octopus.socialnetwork.ui.screen.conversations.messages

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.octopus.socialnetwork.ui.navigation.MainRoute

private const val ROUTE = MainRoute.Messages


fun NavController.navigateToMessage() {
    navigate(ROUTE)
}

fun NavGraphBuilder.messageRoute(navController: NavController) {
    composable(ROUTE) {
        MessageScreen(navController)
    }
}