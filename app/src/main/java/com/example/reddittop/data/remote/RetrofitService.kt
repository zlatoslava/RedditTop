package com.example.reddittop.data.remote

import com.example.reddittop.data.model.RedditApiResponse
import retrofit2.http.GET

interface RetrofitService {

    @GET("top.json")
    suspend fun getPosts(): RedditApiResponse
}