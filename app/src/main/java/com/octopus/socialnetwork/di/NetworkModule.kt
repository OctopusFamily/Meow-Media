package com.octopus.socialnetwork.di

import com.octopus.socialnetwork.BuildConfig
import com.octopus.socialnetwork.data.remote.interceptor.AuthInterceptor
import com.octopus.socialnetwork.data.remote.service.CloudMessagingService
import com.octopus.socialnetwork.data.remote.service.SocialService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideSocialNetworkService(
        retrofit: Retrofit,
    ): SocialService {
        return retrofit.create(SocialService::class.java)
    }

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        ): OkHttpClient {
        val builder = OkHttpClient()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .callTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideFirebaseCloudMessagingApi(factory: GsonConverterFactory): CloudMessagingService =
        Retrofit.Builder()
            .baseUrl(CloudMessagingService.BASE_URL)
            .addConverterFactory(factory)
            .build()
            .create(CloudMessagingService::class.java)

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

}
