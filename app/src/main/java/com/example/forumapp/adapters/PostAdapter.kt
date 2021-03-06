package com.example.forumapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forumapp.databinding.PostItemBinding
import com.example.forumapp.models.PostWithCreatorName
import com.example.forumapp.models.network.Post
import com.example.forumapp.ui.PostActivity

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var postList: List<PostWithCreatorName> = emptyList()
    inner class PostViewHolder (var binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val postItemBinding: PostItemBinding = PostItemBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(postItemBinding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.apply {
            username.text = postList[position].userCreatorName
            title.text = postList[position].title
            descriptionPreview.text = postList[position].body.replace('\n', ' ')


            viewMoreButton.setOnClickListener {
                val postIntent = Intent(it.context, PostActivity::class.java)
                postIntent.putExtra("postId", postList[position].id)

                it.context.startActivity(postIntent)
            }
        }
    }

    override fun getItemCount(): Int = postList.size

    fun setData(newList: List<PostWithCreatorName>) {
        postList = newList
        notifyDataSetChanged()
    }



}