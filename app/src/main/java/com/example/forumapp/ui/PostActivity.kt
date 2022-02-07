package com.example.forumapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forumapp.R
import com.example.forumapp.adapters.CommentListAdapter
import com.example.forumapp.databinding.ActivityPostBinding
import com.example.forumapp.models.PostWithCreatorName
import com.example.forumapp.models.enum.EnumResponse
import com.example.forumapp.models.network.PostWithUserData
import com.example.forumapp.viewmodels.PostActivityVMFactory
import com.example.forumapp.viewmodels.PostActivityViewModel

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    private lateinit var postActivityViewModel: PostActivityViewModel
    private var postId: Int = 0

    private val commentListAdapter by lazy { CommentListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setupRecyclerView()
        setupObserversOnViewModel()
        if (postActivityViewModel.postLiveData.value != null) {
            postActivityViewModel.getPostContent()
        }

        if (postActivityViewModel.commentListLiveData.value!!.data.isEmpty()) {
            postActivityViewModel.getComments()
        }


    }

    private fun setupObserversOnViewModel() {
        postActivityViewModel.apply {
            commentListLiveData.observe(this@PostActivity, Observer {
                if (it.enumResponse == EnumResponse.DONE) {
                    commentListAdapter.setData(commentListLiveData.value!!.data)
                }
            })

            postLiveData.observe(this@PostActivity, Observer {
                if (it != null && it.enumResponse == EnumResponse.DONE) {
                    setPostContent(it.data)
                }
            })
        }
    }

    private fun setPostContent(data: PostWithCreatorName) {
        binding.apply {
            postTitle.text = data.title
            postCreator.text = data.userCreatorName
            postContent.text = data.body
        }
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        postId = intent.getIntExtra("postId", 0)
        postActivityViewModel = ViewModelProvider(this,
            PostActivityVMFactory(postId)).get(PostActivityViewModel::class.java)
        postActivityViewModel.getPostContent()
    }

    private fun setupRecyclerView() {
        binding.commentListOnPost.apply {
            adapter = commentListAdapter
            layoutManager = LinearLayoutManager(this@PostActivity)
        }
    }
}