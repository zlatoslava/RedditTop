package com.example.reddittop.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reddittop.data.model.RedditPost
import com.example.reddittop.data.model.RedditPostContainer
import com.example.reddittop.databinding.ListItemPostBinding

class TopPostsAdapter(val clickListener: OnPostClickListener) : ListAdapter<RedditPostContainer, TopPostsAdapter.ViewHolder>(TopPostsDiffCallback()){

    class ViewHolder (val binding: ListItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPostBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            post: RedditPostContainer,
            clickListener: OnPostClickListener
        ) {
            binding.post = post.data
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener) //TODO: getItem(position)!! check
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class TopPostsDiffCallback : DiffUtil.ItemCallback<RedditPostContainer>() {

    override fun areItemsTheSame(oldItem: RedditPostContainer, newItem: RedditPostContainer): Boolean {
        return oldItem.data.id == newItem.data.id
    }

    override fun areContentsTheSame(oldItem: RedditPostContainer, newItem: RedditPostContainer): Boolean {
        return oldItem == newItem
    }
}

class OnPostClickListener(val clickListener: (imageUrl: String) -> Unit) {
    fun onClick(post: RedditPost) = clickListener(post.bigImageUrl)
}