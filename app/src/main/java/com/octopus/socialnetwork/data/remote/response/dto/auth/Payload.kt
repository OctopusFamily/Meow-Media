package com.octopus.socialnetwork.data.remote.response.dto.auth


import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("guid")
    val guid: Int?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("fullname")
    val fullName: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("birthdate")
    val birthDate: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("icon")
    val icon: Icon?,
    @SerializedName("cover_url")
    val coverUrl: Boolean?,
    @SerializedName("language")
    val language: String?,
)
