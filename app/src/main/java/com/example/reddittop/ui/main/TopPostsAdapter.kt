package com.example.reddittop.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reddittop.data.model.RedditPost
import com.example.reddittop.data.model.RedditPostContainer
import com.example.reddittop.databinding.ListItemPostBinding

class TopPostsAdapter(val clickListener: OnPostClickListener) :
    PagingDataAdapter<RedditPost, TopPostsAdapter.ViewHolder>(TopPostsDiffCallback()){

    class ViewHolder (val binding: ListItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPostBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            post: RedditPost,
            clickListener: OnPostClickListener
        ) {
            binding.post = post
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class TopPostsDiffCallback : DiffUtil.ItemCallback<RedditPost>() {

    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem == newItem
    }
}

class OnPostClickListener(val clickListener: (imageUrl: String?) -> Unit) {
    fun onClick(post: RedditPost) = clickListener(post.bigImageUrl)
}