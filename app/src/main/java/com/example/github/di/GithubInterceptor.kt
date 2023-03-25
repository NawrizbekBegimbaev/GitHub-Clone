package com.example.github.di

import com.example.github.data.models.data.LocalStorage
import okhttp3.Interceptor
import okhttp3.Response

class GithubInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain) : Response {
        val request =
            chain.request().newBuilder().addHeader("Authorization","Bearer ${LocalStorage().token}").build()
        return chain.proceed(request)
    }
}