package com.octopus.socialnetwork.ui.screen.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octopus.socialnetwork.domain.usecase.notifications.FetchNotificationItemsUseCase
import com.octopus.socialnetwork.domain.usecase.notifications.FetchUserNotificationsUseCase
import com.octopus.socialnetwork.ui.screen.notifications.mapper.toNotificationsUiState
import com.octopus.socialnetwork.ui.screen.notifications.state.NotificationItemsUiState
import com.octopus.socialnetwork.ui.screen.notifications.state.NotificationsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val fetchUserNotifications: FetchUserNotificationsUseCase,
    private val fetchNotificationItems: FetchNotificationItemsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationsUiState())
    val state = _state.asStateFlow()

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userNotifications =
                    fetchUserNotifications().map { it.toNotificationsUiState() }

                _state.update {
                    it.copy(
                        notifications = userNotifications,
                        isLoading = false,
                        isError = false,
                    )
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }

    }


    fun markViewedNotification(notification: NotificationItemsUiState) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!notification.viewed)
                    fetchNotificationItems(notification.id)
                getNotifications()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        viewed = false,
                        isError = true
                    )
                }
            }
        }
    }

}