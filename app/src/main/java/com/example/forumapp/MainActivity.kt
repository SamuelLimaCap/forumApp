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

    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postListViewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
        init()

        postListViewModel.postList.observe(this, Observer {
            binding.loadingPostItems.visibility = View.VISIBLE
            postAdapter.setData(it)
            binding.loadingPostItems.visibility = View.GONE
        } )

        postListViewModel.getPosts()


    }

     val onScrollListener = object : RecyclerView.OnScrollListener() {
         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
             super.onScrollStateChanged(recyclerView, newState)

             val TAG = "MainActivity"
             if (!recyclerView.canScrollVertically(1)) {
                 Log.e(TAG, "1")
                 //TODO adicionar items
                 // Problema: Quando o scroll chega no fim ele spamma. Fazer de um jeito que
                 //    quando spammar, faça o pedido apenas 1 vez
             }
         }
     }



     private fun init() {
         binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
         setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(this)
        binding.rvPost.addOnScrollListener(onScrollListener)
    }
}