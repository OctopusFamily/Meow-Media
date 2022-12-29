package com.octopus.socialnetwork.domain.mapper.notifications

import com.octopus.socialnetwork.data.remote.response.dto.notifications.*
import com.octopus.socialnetwork.domain.model.notifications.*
import com.octopus.socialnetwork.domain.utils.toFormattedDate
import java.util.*

fun NotificationsResponse.toUserNotifications(): Notifications {
    return Notifications(
        notifications = list?.map { it.toNotificationItems() } ?: emptyList(),
        count = count ?: 0,
        offset = offset ?: 0
    )
}

fun NotificationItemsDto.toNotificationItems(): NotificationItem {
    return NotificationItem(
        notification = notification?.toNotification() ?: Notification(
            0,
            "",
            0,
            0,
            0,
            false,
            Date(),
            0
        ),
        postOwner = postOwner?.toPoster() ?: PostOwner(0, "", ""),
        entity = entity ?: false,
        post = post ?: false,
    )
}

fun NotificationDto.toNotification(): Notification {
    return Notification(
        notificationId = notificationId ?: 0,
        type = type ?: "",
        notificationUserId = notificationUserId ?: 0,
        subjectOwnerId = subjectOwnerId ?: 0,
        subjectId = subjectId ?: 0,
        viewed = viewed != null,
        timeCreated = timeCreated.toFormattedDate(),
        itemId = itemGuid ?: 0,
    )
}

fun PostOwnerDto.toPoster(): PostOwner {
    return PostOwner(
        userId = userId ?: 0,
        fullName = fullName ?: "",
        icon = icon ?: "",
    )
}


fun NotificationsCountDto.toNotificationsCount(): NotificationsCount {
    return NotificationsCount(
        notifications = notifications ?: 0,
        messages = messages ?: 0,
        friends = friends ?: 0,
    )
}