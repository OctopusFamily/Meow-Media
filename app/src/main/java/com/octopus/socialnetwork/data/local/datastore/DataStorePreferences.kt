package com.octopus.socialnetwork.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface DataStorePreferences {
    fun readBoolean(key: String): Flow<Boolean?>
    suspend fun writeBoolean(key: String, value: Boolean)
    fun readString(key: String): Int?
    suspend fun writeString(key: String, value: Int)
}