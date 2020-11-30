package com.example.reddittop.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditClient {
    companion object {
        private const val BASE_URL = "https://www.reddit.com/"
        private var retrofit: RetrofitService? = null

        fun getRetrofitService(): RetrofitService {
            when (retrofit) {
                null -> retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory.create()
                    )
                    .build().create(RetrofitService::class.java)
            }
            return retrofit as RetrofitService
        }
    }
}