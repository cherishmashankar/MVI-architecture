package com.example.android.mvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.android.mvi.ui.main.state.MainViewState
import com.example.android.mvi.util.*
import com.example.android.mvi.util.Constants.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val results = MediatorLiveData<DataState<ViewStateType>>()

    init{
        results.value = DataState.loading(true)

        GlobalScope.launch(IO){
            delay(TESTING_NETWORK_DELAY)

            withContext(Main){
                val apiResponse = createCall()
                results.addSource(apiResponse){response ->
                    results.removeSource(apiResponse)
                    handleNetworkCall(response)

                }
            }

        }
    }

    fun handleNetworkCall(response: GenericApiResponse<ResponseObject>){
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)

            }

            is ApiEmptyResponse -> {
                println("Debug: NetworkBoundResource: Http 204,returned")
                onReturnError("Http 204,returned")
            }

            is ApiErrorResponse -> {
                println("Debug: NetworkBoundResource: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }

    }

    }
    fun onReturnError(message: String){
        results.value = DataState.error(message)
    }
    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = results as LiveData<DataState<ViewStateType>>

}