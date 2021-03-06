package com.example.forumapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forumapp.models.PostWithCreatorName
import com.example.forumapp.models.Response
import com.example.forumapp.models.enum.EnumResponse
import com.example.forumapp.models.network.Comment
import com.example.forumapp.models.network.PostWithUserData
import com.example.forumapp.repository.PostRepository
import kotlinx.coroutines.launch
import java.io.IOException

class PostActivityViewModel(private val postId: Int) : ViewModel() {


    var postLiveData: MutableLiveData<Response<PostWithCreatorName>> = MutableLiveData(null)
    var commentListLiveData: MutableLiveData<Response<List<Comment>>> = MutableLiveData(
        Response(emptyList(), EnumResponse.ERROR)
    )

    fun getPostContent() {
        viewModelScope.launch {
            val response = try {
                PostRepository.retrofit.getPostAndUserCreator(postId)
            } catch (e: IOException) { return@launch }

            if (response.isSuccessful) {

                postLiveData.postValue(Response(PostWithCreatorName(response.body()!!), EnumResponse.DONE))
            }
        }
    }
    fun getComments() {
        viewModelScope.launch {
            val response = try {
                    PostRepository.retrofit.getCommentsById(postId = postId)
                } catch (e: IOException) {
                    return@launch
                }

            if (response.isSuccessful) {
                var newList: MutableList<Comment> = commentListLiveData.value!!.data.toMutableList()
                newList.addAll(response.body()!!)
                commentListLiveData.postValue(
                    Response(newList.toList(), EnumResponse.DONE)
                )
            }


        }
    }
}