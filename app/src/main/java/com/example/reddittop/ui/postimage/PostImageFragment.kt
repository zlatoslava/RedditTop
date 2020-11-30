package com.example.reddittop.ui.postimage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.reddittop.R
import com.example.reddittop.databinding.FragmentPostImageBinding
import com.example.reddittop.databinding.TopPostsFragmentBinding

class PostImageFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPostImageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_post_image, container, false
        )
        return binding.root
    }


}