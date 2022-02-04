package com.example.forumapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.forumapp.R
import com.example.forumapp.databinding.PostItemBinding
import com.example.forumapp.network.model.Post
import com.example.forumapp.repository.PostRepository
import retrofit2.HttpException
import java.io.IOException

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var postList: List<Post> = emptyList()
    inner class PostViewHolder (var binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val postItemBinding: PostItemBinding = PostItemBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(postItemBinding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.username.text = postList[position].userId.toString()
        holder.binding.title.text = postList[position].title
        holder.binding.descriptionPreview.text = postList[position].body.replace('\n', ' ')
    }

    override fun getItemCount(): Int = postList.size

    fun setData(newList: List<Post>) {
        postList = newList
        notifyDataSetChanged()
    }



}