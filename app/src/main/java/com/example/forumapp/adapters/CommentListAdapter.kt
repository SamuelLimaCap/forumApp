package com.example.forumapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forumapp.R
import com.example.forumapp.databinding.CommentItemBinding
import com.example.forumapp.databinding.PostItemBinding

class CommentListAdapter : RecyclerView.Adapter<CommentListAdapter.CommentListViewHolder>() {

    val commentList = listOf("oi", "oi", "oi")
    lateinit var binding: CommentItemBinding
    inner class CommentListViewHolder(var binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingInflate = CommentItemBinding.inflate(inflater, parent, false)
        return CommentListViewHolder(bindingInflate)
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.binding.apply {
            commentUsername.text = "user name"
            commentContent.text = context.resources.getText(R.string.lorem_ipsum)
        }
    }

    override fun getItemCount(): Int = commentList.size
}