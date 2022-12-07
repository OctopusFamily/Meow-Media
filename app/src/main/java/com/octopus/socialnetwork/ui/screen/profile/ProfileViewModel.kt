package com.octopus.socialnetwork.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octopus.socialnetwork.domain.usecase.user.*
import com.octopus.socialnetwork.ui.screen.profile.mapper.toProfilePostsUiState
import com.octopus.socialnetwork.ui.screen.profile.mapper.toProfileUiState
import com.octopus.socialnetwork.ui.screen.profile.uistate.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchUserDetailS: FetchUserDetailsUseCase,
    private val fetchUserFriendsCount: FetchUserFriendsUseCase,
    private val fetchUserPosts: FetchUserPostsUseCase,
    private val addFriendUseCase: AddFriendUseCase,
    private val removeFriendUseCase: RemoveFriendUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    init {
        getUserDetails(20, 20)
    }

    private fun getUserDetails(currentUserId: Int, visitedUserId: Int) {
        try {
            viewModelScope.launch {
                val userFriendsCount = fetchUserFriendsCount(currentUserId).total
                val profilePosts =
                    fetchUserPosts(currentUserId, visitedUserId).posts.toProfilePostsUiState()
                val userPostsCount = fetchUserPosts(currentUserId, visitedUserId).count
                val profileUiState = fetchUserDetailS(currentUserId).toProfileUiState()

                _state.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        fullName = profileUiState.fullName,
                        username = profileUiState.username,
                        friendsCount = userFriendsCount.toString(),
                        postCount = userPostsCount.toString(),
                        profileAvatar = profileUiState.profileAvatar,
                        profileCover = profileUiState.profileCover,
                        profilePosts = profilePosts
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    isSuccess = false,
                    isError = true
                )
            }
        }
    }

    fun onClickFollow() {
        viewModelScope.launch {
            if (!_state.value.isRequestExists) {
                val result = addFriendUseCase(30, 20)
                _state.update {
                    it.copy(
                        isRequestExists = result.requestExists,
                        isRequestSent = result.success,
                        isFriend = result.isFriend
                    )
                }
            } else {
                removeFriendUseCase(30, 20)
                _state.update {
                    it.copy(
                        isRequestExists = false,
                        isRequestSent = false,
                        isFriend = false,
                    )
                }
            }

        }
    }

    fun onClickMessage() {
        //
    }
}