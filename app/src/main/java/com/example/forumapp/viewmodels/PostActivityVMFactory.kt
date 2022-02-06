package com.example.forumapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PostActivityVMFactory(val postId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostActivityViewModel(postId) as T
    }
}