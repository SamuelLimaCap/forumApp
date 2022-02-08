package com.example.forumapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forumapp.models.PostWithCreatorName
import com.example.forumapp.models.Response
import com.example.forumapp.models.enum.EnumResponse
import com.example.forumapp.models.network.Post
import com.example.forumapp.models.network.PostWithUserData
import com.example.forumapp.repository.PostRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PostListViewModel : ViewModel(){
    private var page: Int = 1
    var postList: MutableLiveData<Response<List<PostWithCreatorName>>> = MutableLiveData(
        Response(emptyList(), EnumResponse.DONE)
    )
    val isStatusCallLoading : MutableLiveData<Boolean> = MutableLiveData(false);

     fun getPosts(){
         var coroutine = viewModelScope.launch {
             isStatusCallLoading.postValue(true)

            val response = try { PostRepository.retrofit.getPostsGroup10(page) }
            catch (e: IOException) {
                Log.e("PostListViewMOdel","Error on catching value from web ")
                addErrorToPostList()
                isStatusCallLoading.postValue(false)
                return@launch
            }
            catch (e: HttpException) {
                Log.e("PostListViewMOdel","Don't have internet connection")
                addErrorToPostList()
                isStatusCallLoading.postValue(false)
                return@launch
            }

            if (response.isSuccessful) {
                addToPostList(response.body()!!)
                isStatusCallLoading.postValue(false)
                page++;
            } else {

            }
        }
    }


    private fun addToPostList(list: List<PostWithUserData>) {
        val newList: MutableList<PostWithCreatorName> = postList.value!!.data.toMutableList()
        newList.addAll(list.map { postWithUserData -> PostWithCreatorName(postWithUserData) })
        postList.postValue(Response(newList.toList(), EnumResponse.DONE))
    }

    private fun addErrorToPostList() {
        postList.postValue(Response(postList.value!!.data, EnumResponse.ERROR))
    }
}