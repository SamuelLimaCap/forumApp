package com.example.forumapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forumapp.network.model.Post
import com.example.forumapp.repository.PostRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PostListViewModel : ViewModel(){
    private var page: Int = 1
    var postList: MutableLiveData<List<Post>> = MutableLiveData(emptyList())

     fun getPosts() {
         var coroutine = viewModelScope.launch {
            val response = try { PostRepository.retrofit.getGroup10(page) }
            catch (e: IOException) {
                Log.e("PostListViewMOdel","Error on catching value from web "); return@launch }
            catch (e: HttpException) {
                Log.e("PostListViewMOdel","Don't have internet connection"); return@launch}

            if (response.isSuccessful) {
                addToPostList(response.body()!!)
                page++;
            }
        }
    }

    private fun addToPostList(list: List<Post>) {
        val newList: MutableList<Post> = postList.value!!.toMutableList()
        newList.addAll(list.toMutableList())
        postList.postValue(newList.toList())
    }
}