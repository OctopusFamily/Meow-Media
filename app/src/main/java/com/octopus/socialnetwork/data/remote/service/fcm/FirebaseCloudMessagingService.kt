package com.octopus.socialnetwork.data.remote.service.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.octopus.socialnetwork.data.remote.response.dto.messages.NotificationData
import com.octopus.socialnetwork.data.remote.response.dto.messages.NotificationKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class FirebaseCloudMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {

        Log.d("MESSAGING", "From: ${message}")
        if (message.data.isNotEmpty()) {
            Log.d("MESSAGING", "Message data payload: ${message.data}")
        }
        message.data.let { data ->
            Log.i("MESSAGING", "onMessageReceived $data")
            if (data.isNotEmpty()) {
                val id = (data[NotificationKeys.ID_KEY]?.toInt() ?: 0)
                val friendId = (data[NotificationKeys.FRIEND_ID_KEY]?.toInt() ?: 0)
                val message = (data[NotificationKeys.MESSAGE_KEY]).toString()
                val time = (data[NotificationKeys.TIME_KEY]).toString()

                GlobalScope.launch(Dispatchers.IO) {
                    events.emit(
                        NotificationData(
                            id = id,
                            friendId = friendId,
                            message = message,
                            time = time,
                        )
                    )
                }
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.v("MESSAGING", "onNewToken $token")
    }

    companion object {
        val events = MutableSharedFlow<NotificationData>()
    }

}
