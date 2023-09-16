package com.obidia.core.data.di.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newReq = chain.request().newBuilder().addHeader(
            "Authorization",
            "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"
        ).build()

        return chain.proceed(newReq)
    }

}