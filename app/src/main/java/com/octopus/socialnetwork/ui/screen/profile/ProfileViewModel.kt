package com.octopus.socialnetwork.ui.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octopus.socialnetwork.domain.usecase.user_details.FetchUserDetailsUseCase
import com.octopus.socialnetwork.domain.usecase.user_details.FetchUserFriendsUseCase
import com.octopus.socialnetwork.domain.usecase.user_details.FetchUserPostsCountUseCase
import com.octopus.socialnetwork.ui.screen.profile.uistate.ProfileUiState
import com.octopus.socialnetwork.ui.screen.profile.uistate.asProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private val  fetchUserDetailsUseCase: FetchUserDetailsUseCase,
    private val  fetchUserFriendsUseCase: FetchUserFriendsUseCase,
    private val  fetchUserDetailsCountUseCase: FetchUserPostsCountUseCase
) : ViewModel(){

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    init {
        getUserDetails(16, 28)
    }

    private fun getUserDetails(currentUserId: Int, visitedUserId: Int){
        try {
            viewModelScope.launch {
                val userFriendsCount = fetchUserFriendsUseCase(currentUserId).total
                val userPostsCount = fetchUserDetailsCountUseCase(currentUserId, visitedUserId)
                val profileUiState = fetchUserDetailsUseCase(currentUserId).asProfileUiState(userFriendsCount, userPostsCount)
                updateUiState(profileUiState)
            }
        } catch (e: Exception){

        }
    }

    private fun updateUiState(profileUiState: ProfileUiState){
        _state.update {
            it.copy(
                fullName = profileUiState.fullName,
                username = profileUiState.username,
                friendsCount = profileUiState.friendsCount,
                postCount = profileUiState.postCount,
                profileAvatar = profileUiState.profileAvatar,
                profileCover = profileUiState.profileCover
            )
        }

        Log.i("PROFILE_INFO","PROFILE_INFO ${profileUiState.username}")
    }



    fun onClickFollow(){
        //
    }

    fun onClickMessage(){
        //
    }
}