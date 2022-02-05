package com.example.forumapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forumapp.adapters.PostAdapter
import com.example.forumapp.databinding.ActivityMainBinding
import com.example.forumapp.viewmodels.PostListViewModel

class MainActivity : AppCompatActivity() {

    private val postAdapter by lazy { PostAdapter() }
    private lateinit var postListViewModel: PostListViewModel
    var isLoading = false
    var wasOnBottom = false

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        setupRecyclerView()
        setupLiveDataObserver()
        if (postListViewModel.postList.value!!.isEmpty()) postListViewModel.getPosts()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        postListViewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
    }

    private fun setupRecyclerView() {
        postAdapter.addData(postListViewModel.postList.value!!)
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(this)
        binding.rvPost.addOnScrollListener(onScrollListener)
    }

    private fun setupLiveDataObserver() {
        postListViewModel.postList.observe(this, Observer {
            binding.loadingPostItems.visibility = View.VISIBLE
            postAdapter.setData(it)
            binding.loadingPostItems.visibility = View.GONE
            isLoading = false
            wasOnBottom = false


        })
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (!recyclerView.canScrollVertically(1)) {
                if (!wasOnBottom && !isLoading) {
                    wasOnBottom = true
                    isLoading = true
                    postListViewModel.getPosts()
                }
            }
        }
    }

}