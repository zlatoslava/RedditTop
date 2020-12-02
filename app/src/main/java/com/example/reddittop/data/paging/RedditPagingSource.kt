package com.example.reddittop.data.paging

import androidx.paging.PagingSource
import com.example.reddittop.data.model.RedditPost
import com.example.reddittop.data.remote.RetrofitService
import retrofit2.HttpException
import java.io.IOException

class RedditPagingSource(private val service: RetrofitService) :
    PagingSource<String, RedditPost>() {

    override val keyReuseSupported: Boolean = true   //enables PagingSource to reuse keys in fetching the posts

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditPost> {
        return try {
            val response = service.fetchPosts(loadSize = params.loadSize, after = params.key)
            val listing = response.body()?.data
            val redditPosts = listing?.children?.map { it.data }    //transforming list of RedditPostContainer into RedditPost
            LoadResult.Page(
                redditPosts ?: listOf(),
                listing?.before,
                listing?.after
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}