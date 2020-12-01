package com.example.reddittop.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.reddittop.data.paging.RedditPagingSource
import com.example.reddittop.data.remote.RedditClient

class TopPostsViewModel : ViewModel() {

     val _posts = Pager(PagingConfig(
        pageSize = 10, enablePlaceholders = false)) {
        RedditPagingSource(RedditClient.getRetrofitService())
    }.flow.cachedIn(viewModelScope)

}