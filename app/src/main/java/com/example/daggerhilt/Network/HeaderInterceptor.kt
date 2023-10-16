package com.example.daggerhilt.Network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(val token: Boolean, val fname: String,) : Interceptor {
    @ApplicationContext
    val context: Context? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        val orignalRequest = chain.request()

        val requestBuilder = orignalRequest.newBuilder()
        val request = chain.request()
        val httpurl = request.url
        val urlString = httpurl.toString()
        return chain.proceed(requestBuilder.build())

    }
}
