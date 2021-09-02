package com.example.android.mvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.mvi.api.ApiService
import com.example.android.mvi.api.RetrofitBuilder
import com.example.android.mvi.ui.main.state.MainViewState
import com.example.android.mvi.util.ApiEmptyResponse
import com.example.android.mvi.util.ApiErrorResponse
import com.example.android.mvi.util.ApiSuccessResponse
import com.example.android.mvi.util.DataState

//Singleton
object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(blogPost = apiResponse.body)

                                )

                            }

                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    message = "Http 204,returned"
                                )

                            }

                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                )

                            }
                        }
                    }
                }
            }

    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(users = apiResponse.body)
                                )
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    message = "Http 204,returned"
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }
                        }
                    }
                }
            }
    }

}