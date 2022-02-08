package com.example.forumapp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forumapp.R
import com.example.forumapp.adapters.PostAdapter
import com.example.forumapp.databinding.ActivityMainBinding
import com.example.forumapp.models.PostWithCreatorName
import com.example.forumapp.models.Response
import com.example.forumapp.models.enum.EnumResponse
import com.example.forumapp.models.network.Post
import com.example.forumapp.viewmodels.PostListViewModel

class MainActivity : AppCompatActivity() {

    private val postAdapter by lazy { PostAdapter() }
    private lateinit var postListViewModel: PostListViewModel
    var isLoadingNewItems = false
    var scrollWasOnBottom = false

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setupRecyclerView()
        setupLiveDataObserver()
        setupErrorButtonListener()
        setupInitialActions()

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
            handleGetPosts(it)
        })
        postListViewModel.isStatusCallLoading.observe(this) {
            if (it) showLoadingIcon() else hideLoadingIcon()
        }
    }

    private fun setupErrorButtonListener() {
        binding.buttonErrorMessage.setOnClickListener {
            getPosts()
        }
    }

    private fun setupInitialActions() {
        if (postListViewModel.postList.value!!.data.isEmpty()) {
            getPosts()
        }
    }

    /*
        END OF onCreate() methods
     */

    private fun getPosts() {
        hideErrorMessageAndButton()
        postListViewModel.getPosts()
    }

    private fun handleGetPosts(response: Response<List<PostWithCreatorName>>) {
        if (response.enumResponse == EnumResponse.DONE) {
            postAdapter.setData(response.data)
        } else {
            showErrorMessageAndButton()
        }
        isLoadingNewItems = false
        scrollWasOnBottom = false
    }


    private fun showLoadingIcon() {
        binding.loadingIcon.visibility = View.VISIBLE
    }

    private fun hideLoadingIcon() {
        binding.loadingIcon.visibility = View.GONE
    }

    private fun showErrorMessageAndButton() {
        binding.errorLayout.visibility = View.VISIBLE
        binding.errorLayout.allViews.forEach { it.visibility = View.VISIBLE }
    }

    private fun hideErrorMessageAndButton() {
        binding.errorLayout.visibility = View.GONE
        binding.errorLayout.allViews.forEach { it.visibility = View.GONE }
    }

    /*
        Listeners
     */

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) {
                if (!scrollWasOnBottom && !isLoadingNewItems) {
                    scrollWasOnBottom = true
                    isLoadingNewItems = true
                    getPosts()
                }
            }
        }
    }

}