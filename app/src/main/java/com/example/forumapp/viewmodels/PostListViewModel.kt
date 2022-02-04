package com.example.forumapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.forumapp.network.model.Post
import com.example.forumapp.repository.PostRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PostListViewModel : ViewModel(){
    var page: Int = 1
    var postList: MutableLiveData<List<Post>> = MutableLiveData()
    var postListResponse: Post? = null


     fun getPosts() {
        viewModelScope.launch {
            Log.i("MainActivity", "MainActivityCoroutine")
            val response = try { PostRepository.retrofit.getGroup10(1) }
            catch (e: IOException) {return@launch }
            catch (e: HttpException) { return@launch}

            if (response.isSuccessful) {
                response.body()?.let {
                    postList.postValue(it)
                }
            }
        }
    }
}