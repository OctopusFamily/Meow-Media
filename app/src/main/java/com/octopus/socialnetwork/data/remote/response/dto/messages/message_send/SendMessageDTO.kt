package com.octopus.socialnetwork.data.remote.response.dto.messages.message_send

import com.google.gson.annotations.SerializedName
import com.octopus.socialnetwork.data.remote.response.dto.messages.MessageUserDTO

data class SendMessageDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("message_from")
    val messageSender: MessageUserDTO?,
    @SerializedName("message_to")
    val messageReceiver: MessageUserDTO?,
    @SerializedName("time")
    val time: Int?,
    @SerializedName("viewed")
    val viewed: String?
)