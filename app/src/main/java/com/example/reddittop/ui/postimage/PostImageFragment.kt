package com.example.reddittop.ui.postimage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.reddittop.R
import com.example.reddittop.databinding.FragmentPostImageBinding

class PostImageFragment : Fragment() {

    private lateinit var binding: FragmentPostImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_post_image, container, false
        )

        loadImage(arguments?.getString("imageUrl") ?: "something") //TODO: change

        return binding.root
    }

    private fun loadImage(imageUrl: String?) {
        Glide.with(this)
            .load(imageUrl)
            .into(binding.postBigImageView)
    }


}