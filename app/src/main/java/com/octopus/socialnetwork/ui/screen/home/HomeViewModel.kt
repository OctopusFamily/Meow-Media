package com.octopus.socialnetwork.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.octopus.socialnetwork.domain.usecase.like.ToggleLikeUseCase
import com.octopus.socialnetwork.domain.usecase.notifications.FetchNotificationsCountUseCase
import com.octopus.socialnetwork.domain.usecase.post.FetchNewsFeedPostsUseCase
import com.octopus.socialnetwork.domain.usecase.user.friend_requests.FetchFriendRequestsListUseCase
import com.octopus.socialnetwork.ui.screen.home.state.HomeUiState
import com.octopus.socialnetwork.ui.screen.post.mapper.toPostUiState
import com.octopus.socialnetwork.ui.screen.profile.mapper.toUserDetailsUiState
import com.octopus.socialnetwork.ui.util.Constants.POST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toggleLike: ToggleLikeUseCase,
    private val fetchNotificationsCount: FetchNotificationsCountUseCase,
    private val fetchFriendRequestsList: FetchFriendRequestsListUseCase,
    private val fetchNewsFeed: FetchNewsFeedPostsUseCase,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        getNewsFeed()
        getFriendRequestsCount()
        getNotificationsCount()
    }

    private fun getNewsFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val posts = fetchNewsFeed().cachedIn(viewModelScope).map { pagingData ->
                    pagingData.map { post -> post.toPostUiState() }
                }
                _homeUiState.update {
                    it.copy(posts = posts, isLoading = false)
                }
            } catch (e: Exception) {
                _homeUiState.update {
                    it.copy(isLoading = false, isError = true)
                }
            }
        }
    }

    fun onClickLike(postId: Int, totalLikes: Int, isLiked: Boolean) {
        viewModelScope.launch {
            try {
                toggleLike(postId, totalLikes, isLiked, POST)
                _homeUiState.update { it.copy(isError = false) }

            } catch (e: Exception) {

            }
        }
    }


    private fun getFriendRequestsCount() {
        viewModelScope.launch {
            try {
                val friendRequestsCount =
                    fetchFriendRequestsList().map { it.toUserDetailsUiState() }.size
                _homeUiState.update { it.copy(friendRequestsCount = friendRequestsCount) }
            } catch (e: Exception) {
                _homeUiState.update { it.copy(friendRequestsCount = 0) }
            }
        }
    }

    private fun getNotificationsCount() {
        viewModelScope.launch {
            try {
                val currentNotificationsCount = fetchNotificationsCount().notifications
                _homeUiState.update { it.copy(notificationsCount = currentNotificationsCount) }
            } catch (e: Exception) {
                _homeUiState.update { it.copy(notificationsCount = 0) }
            }
        }
    }

    fun onClickTryAgain() {
        getNewsFeed()
        getFriendRequestsCount()
        getNotificationsCount()
    }
}