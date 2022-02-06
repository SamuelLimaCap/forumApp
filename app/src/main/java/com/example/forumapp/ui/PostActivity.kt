package com.example.forumapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forumapp.R
import com.example.forumapp.adapters.CommentListAdapter
import com.example.forumapp.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostBinding
    private val commentListAdapter by lazy { CommentListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setupRecyclerView()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
    }

    private fun setupRecyclerView() {
        binding.commentListOnPost.apply {
            adapter = commentListAdapter
            layoutManager = LinearLayoutManager(this@PostActivity)
        }
    }
}