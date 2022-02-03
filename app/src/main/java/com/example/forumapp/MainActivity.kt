 package com.example.forumapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forumapp.adapters.PostAdapter
import com.example.forumapp.databinding.ActivityMainBinding
import com.example.forumapp.repository.PostRepository
import retrofit2.HttpException
import java.io.IOException

 class MainActivity : AppCompatActivity() {

    private val postAdapter by lazy { PostAdapter() }


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            val response = try {
                PostRepository.retrofit.getAll()
            } catch (e: IOException) {
                return@launchWhenCreated
            } catch (e: HttpException) {
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                postAdapter.setData(response.body()!!)
            }
        }

    }

    private fun init() {

    }

    private fun setupRecyclerView() {
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(this)
    }
}