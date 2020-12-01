package com.example.reddittop.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reddittop.R
import com.example.reddittop.databinding.TopPostsFragmentBinding

class TopPostsFragment : Fragment() {

    private lateinit var binding: TopPostsFragmentBinding
    private lateinit var viewModel: TopPostsViewModel
    private lateinit var adapter: TopPostsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(
            inflater, R.layout.top_posts_fragment, container, false
        )

        viewModel = ViewModelProvider(this).get(TopPostsViewModel::class.java)

        adapter  = TopPostsAdapter(OnPostClickListener {
            findNavController().navigate(TopPostsFragmentDirections.actionTopPostsFragmentToPostImageFragment(it))
        })

        initViews()
        initObservers()

        return binding.root
    }

    private fun initViews() {
        binding.topPostsRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.topPostsRecyclerView.adapter = adapter
    }

    private fun initObservers() {
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}