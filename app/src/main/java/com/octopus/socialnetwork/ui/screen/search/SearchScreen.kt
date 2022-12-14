package com.octopus.socialnetwork.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.octopus.socialnetwork.R
import com.octopus.socialnetwork.ui.composable.AppBar
import com.octopus.socialnetwork.ui.composable.ImageForEmptyList
import com.octopus.socialnetwork.ui.composable.SpacerVertical16
import com.octopus.socialnetwork.ui.composable.friend_requests.UserDetailsItem
import com.octopus.socialnetwork.ui.composable.lotties.LottieError
import com.octopus.socialnetwork.ui.composable.lotties.LottieLoading
import com.octopus.socialnetwork.ui.composable.lotties.LottieSearch
import com.octopus.socialnetwork.ui.composable.search.SearchViewItem
import com.octopus.socialnetwork.ui.screen.messaging.chat.navigateToChat
import com.octopus.socialnetwork.ui.screen.profile.navigateToUserProfileScreen
import com.octopus.socialnetwork.ui.screen.search.state.SearchUiState
import com.octopus.socialnetwork.ui.theme.outLine

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    SearchContent(
        state = state,
        onClickMessage = navController::navigateToChat,
        onChangeTypingSearch = viewModel::onChangeQuery,
        onClickItem = { navController.navigateToUserProfileScreen(it) },
        onClickTryAgain = viewModel::onClickTryAgain
    )
}


@Composable
private fun SearchContent(
    state: SearchUiState,
    onChangeTypingSearch: (String) -> Unit,
    onClickItem: (Int) -> Unit,
    onClickMessage: (Int) -> Unit,
    onClickTryAgain: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
    ) {
        AppBar(title = stringResource(R.string.search), showBackButton = false)
        Divider(color = MaterialTheme.colors.outLine, thickness = 1.dp)
        SpacerVertical16()

        SearchViewItem(query = state.query, onValueChange = onChangeTypingSearch)

        if (state.isError) {
            LottieError( onClickTryAgain = onClickTryAgain)
        } else {

                if (state.query.isEmpty()) {
                     LottieSearch()
                } else {
                    if (state.isLoading) {
                         LottieLoading()
                    } else if (state.users.isEmpty()) {
                        ImageForEmptyList(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .fillMaxSize()
                        )
                    } else {
                        LazyColumn {
                        items(state.users) { searchItem ->
                            UserDetailsItem(
                                state = searchItem,
                                onClickItem = onClickItem,
                                onClickMessage = onClickMessage
                            )

                        }
                    }

                }
            }

        }

    }
}


