package com.example.forumapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forumapp.adapters.PostAdapter
import com.example.forumapp.databinding.ActivityMainBinding
import com.example.forumapp.models.enum.EnumResponse
import com.example.forumapp.viewmodels.PostListViewModel
import java.io.IOException

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
        setupErrorButtonListener()
        if (postListViewModel.postList.value!!.response == EnumResponse.ERROR) {
            getPosts()
        }
    }


    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        postListViewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
    }

    private fun setupRecyclerView() {
        postAdapter.setData(postListViewModel.postList.value!!.data)
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(this)
        binding.rvPost.addOnScrollListener(onScrollListener)
    }

    private fun setupLiveDataObserver() {
        postListViewModel.postList.observe(this, Observer {
            binding.loadingPostItems.visibility = View.VISIBLE
            if (it.response == EnumResponse.DONE) {
                postAdapter.setData(it.data)
            } else {
                binding.errorMessage.visibility = View.VISIBLE
            }
            binding.loadingPostItems.visibility = View.GONE
            isLoading = false
            wasOnBottom = false
        })
    }

    private fun setupErrorButtonListener() {
        binding.buttonErrorMessage.setOnClickListener {
            getPosts()
        }
    }

    private fun getPosts() {
        binding.errorMessage.visibility = View.GONE
        binding.loadingPostItems.visibility = View.VISIBLE
        postListViewModel.getPosts()
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                if (!wasOnBottom && !isLoading) {
                    wasOnBottom = true
                    isLoading = true
                    getPosts()
                }
            }
        }
    }

}