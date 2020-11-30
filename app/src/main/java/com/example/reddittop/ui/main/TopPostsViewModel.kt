package com.example.reddittop.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddittop.data.model.RedditPostContainer
import com.example.reddittop.data.remote.RedditClient
import kotlinx.coroutines.launch

class TopPostsViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<RedditPostContainer>>()
    val posts: LiveData<List<RedditPostContainer>>
        get() = _posts

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            val redditApiResponse = RedditClient.getRetrofitService().getPosts()
            _posts.value =redditApiResponse.data.children
        }
    }
}