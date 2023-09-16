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