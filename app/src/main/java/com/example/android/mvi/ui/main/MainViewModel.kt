package com.example.android.mvi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.mvi.ui.main.state.MainViewState

class MainViewModel: ViewModel() {

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    //getter
    val viewState: LiveData<MainViewState>
    get() = _viewState

}