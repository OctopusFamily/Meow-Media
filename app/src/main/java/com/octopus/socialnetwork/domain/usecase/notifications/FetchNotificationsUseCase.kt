package com.octopus.socialnetwork.domain.usecase.notifications

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.octopus.socialnetwork.data.repository.social.SocialRepository
import com.octopus.socialnetwork.domain.mapper.notifications.toNotificationItems
import com.octopus.socialnetwork.domain.model.notifications.NotificationItem
import com.octopus.socialnetwork.domain.usecase.authentication.FetchUserIdUseCase
import com.octopus.socialnetwork.ui.util.Constants.NOTIFICATIONS_TYPES_List
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchNotificationsUseCase @Inject constructor(
    private val socialRepository: SocialRepository,
    private val fetchUserIdUseCase: FetchUserIdUseCase,
) {
    suspend operator fun invoke(): Flow<PagingData<NotificationItem>> {
        val notification =
            socialRepository.getNotificationsPager(fetchUserIdUseCase()).flow.map { pager ->
                pager.map { notificationItemDto -> notificationItemDto.toNotificationItems() }
            }
        val r = filterNotifications(notification)
        Log.e("MALT", "FILTER :$r")
        return r
    }

    private fun filterNotifications(item: Flow<PagingData<NotificationItem>>): Flow<PagingData<NotificationItem>> {
        return item.map { it.filter { it.notification.type in NOTIFICATIONS_TYPES_List } }
    }
}






