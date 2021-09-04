package com.example.android.mvi.repository

import androidx.lifecycle.LiveData
import com.example.android.mvi.api.RetrofitBuilder
import com.example.android.mvi.model.Blogs
import com.example.android.mvi.model.User
import com.example.android.mvi.ui.main.state.MainViewState
import com.example.android.mvi.util.ApiSuccessResponse

import com.example.android.mvi.util.DataState
import com.example.android.mvi.util.GenericApiResponse

//Singleton
object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<Blogs>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Blogs>>) {
                results.value = DataState.data(data = MainViewState(blogPost = response.body)
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<Blogs>>> {
                return RetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()

    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                results.value = DataState.data(data = MainViewState(users = response.body)
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()


    }

}