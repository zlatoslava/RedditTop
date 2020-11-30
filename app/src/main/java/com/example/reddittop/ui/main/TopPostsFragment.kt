package com.example.reddittop.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.reddittop.R
import com.example.reddittop.databinding.TopPostsFragmentBinding

class TopPostsFragment : Fragment() {

    private lateinit var viewModel: TopPostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:TopPostsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.top_posts_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TopPostsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}