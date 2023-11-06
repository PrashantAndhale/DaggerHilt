package com.example.daggerhilt.di

import android.content.Context
import com.example.daggerhilt.BaseApp
import com.example.daggerhilt.Network.ApiService
import com.example.daggerhilt.Network.HeaderInterceptor
import com.example.daggerhilt.Utils.Utility
import com.example.daggerhilt.activities.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @BaseUrl
    @Singleton
    fun getBaseUrl() = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun getIsAddedToken() = true

    @Provides
    @FName
    fun provideFame() = "Dhara"


    @Provides
    @Named("category")
    fun provideCategory(): String {
        return MainActivity.category // Define the category string here
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY) // Set the desired log level
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(isAddedToken: Boolean, @FName fName: String): HeaderInterceptor {
        return HeaderInterceptor(isAddedToken, fName)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpLoggingInterceptor)
        builder.addInterceptor(headerInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    @Message
    fun provideMessage() = "Prashant"


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient, @BaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideApplicationContext(): Context {
        return BaseApp.instance.applicationContext
    }

    @Provides
    fun provideUtility(context: Context): Utility {
        return Utility(context)
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Message

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Category

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl
