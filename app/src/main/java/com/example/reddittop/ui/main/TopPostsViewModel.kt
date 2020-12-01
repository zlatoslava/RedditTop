package com.example.reddittop.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.reddittop.data.model.RedditPost
import com.example.reddittop.data.model.RedditPostContainer
import com.example.reddittop.data.paging.RedditPagingSource
import com.example.reddittop.data.remote.RedditClient
import kotlinx.coroutines.launch

class TopPostsViewModel : ViewModel() {

    private val _posts = Pager(PagingConfig(
        pageSize = 10, enablePlaceholders = false)) {
        RedditPagingSource(RedditClient.getRetrofitService())
    }.liveData.cachedIn(viewModelScope)

    val posts: LiveData<PagingData<RedditPost>>
        get() = _posts

}