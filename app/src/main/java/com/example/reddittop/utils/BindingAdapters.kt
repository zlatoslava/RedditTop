package com.example.reddittop.utils

import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*

@BindingAdapter("postImage")
fun ImageView.setPostImage(thumbnailUrl: String) {
    Glide.with(this.context)
        .load(thumbnailUrl)
        .centerCrop()
        .into(this)
}

@BindingAdapter("date")
fun TextView.setDate(date: Long) {          // timestamp is in seconds, so multiply it to be in milliseconds
    val timeSpan = DateUtils.getRelativeTimeSpanString(date * 1000)
    text = timeSpan.toString()
}

@BindingAdapter("comments")
fun TextView.setComments(commentsCount: Int) {
    val str = "$commentsCount comments"
    text = str
}

@BindingAdapter("author")
fun TextView.setAuthor(author: String) {
    val authorStr = "Posted by $author"
    text = authorStr
}