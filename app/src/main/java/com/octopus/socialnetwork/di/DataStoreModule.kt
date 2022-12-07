package com.octopus.socialnetwork.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.octopus.socialnetwork.data.local.datastore.DataStorePreferences
import com.octopus.socialnetwork.data.local.datastore.DataStorePreferencesImpl
import com.octopus.socialnetwork.di.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStorePreference(dataStore: DataStore<Preferences>): DataStorePreferences {
        return DataStorePreferencesImpl(dataStore)
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.preferencesDataStore
    }

    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(SHARED_PREFERENCES_NAME)



}