package com.octopus.socialnetwork.ui.screen.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octopus.socialnetwork.data.repository.messaging.MessagingRepository
import com.octopus.socialnetwork.domain.usecase.messages.SendMessagesUseCase
import com.octopus.socialnetwork.domain.usecase.messages.chat.GetMessageListUseCase
import com.octopus.socialnetwork.domain.usecase.user.FetchUserDetailsUseCase
import com.octopus.socialnetwork.ui.screen.chat.mapper.toMessageUiState
import com.octopus.socialnetwork.ui.screen.chat.uistate.MessageMainUiState
import com.octopus.socialnetwork.ui.screen.profile.mapper.toUserDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessageList: GetMessageListUseCase,
    private val fetchUserDetailS: FetchUserDetailsUseCase,
    private val sendMessage: SendMessagesUseCase,
    private val messagingRepository: MessagingRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args: ChatScreenArgs = ChatScreenArgs(savedStateHandle)

    private val _state = MutableStateFlow(MessageMainUiState())
    val state = _state.asStateFlow()


    init {
        getMessagesWithUser(args.userId.toInt())
        getUserInfo(args.userId.toInt())
        viewModelScope.launch {
            messagingRepository.onReceiveNotification().collect{
                Log.i("TESTING","collecting the notification! $it")
                getMessagesWithUser(it.friendId)
            }
        }
    }

    fun onTextChange(newValue: String) {
        _state.update { it.copy(message = newValue) }
    }



    private fun getMessagesWithUser(otherUserId: Int) {
        try {
            viewModelScope.launch {
                val messages = getMessageList(otherUserId).map { it.toMessageUiState() }

                _state.update { it -> it.copy(
                    isFail = false, isLoading = false, isSuccess = true,
                    messages = messages)
                }

            }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, isFail = true) }
        }

    }
    private fun getUserInfo(otherUserId: Int){
        viewModelScope.launch {
            try {
                val userInfo = fetchUserDetailS(otherUserId).toUserDetailsUiState()
                 _state.update { it.copy(fullName = userInfo.fullName, profileAvatar = userInfo.profileAvatar) }
            }catch(e:Exception){
                _state.update { it.copy(isFail = true) }

            }
        }
    }
    private fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                val messages = sendMessage(args.userId.toInt(), message).toMessageUiState()
                _state.update { it.copy(
                    message = messages.lastMessage
                ) }
//                getMessagesWithUser(args.userId.toInt())
                _state.update { it.copy(isLoading = false, isFail = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isFail = true) }
            }
        }
    }

    fun onClickSend() {
        sendMessage(_state.value.message)
        _state.update { it.copy(message = "",isSuccess = true) }
    }


}