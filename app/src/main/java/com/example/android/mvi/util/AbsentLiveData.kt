package com.example.android.mvi.util

import androidx.lifecycle.LiveData
import com.example.android.mvi.ui.main.state.MainStateEvent

class AbsentLiveData<T : Any?> private constructor(): LiveData<T>() {

    init {
        // use post instead of set since this can be created on any thread
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }



}