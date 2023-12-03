package com.obidia.core.data.di.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newReq = chain.request().newBuilder().addHeader(
            "Authorization",
            "token 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"
        ).build()

        return chain.proceed(newReq)
    }

}