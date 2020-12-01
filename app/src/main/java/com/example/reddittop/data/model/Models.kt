package com.example.reddittop.data.model

import com.google.gson.annotations.SerializedName

data class RedditApiResponse (
    val data: RedditData
)

data class RedditData (
    val children: List<RedditPostContainer>, val after: String?, val before: String?
)

data class RedditPostContainer (
    val data: RedditPost
)

data class RedditPost (
    @SerializedName("id")
    val id: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("created_utc")
    val date: Long,
    @SerializedName("thumbnail")
    val thumbnailUrl: String,
    @SerializedName("url_overridden_by_dest")
    val bigImageUrl: String,
    @SerializedName("num_comments")
    val commentCount: Int
)

