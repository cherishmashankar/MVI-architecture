package com.example.android.mvi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.mvi.model.Blogs
import com.example.android.mvi.model.User
import com.example.android.mvi.ui.main.state.MainStateEvent
import com.example.android.mvi.ui.main.state.MainViewState
import com.example.android.mvi.ui.main.state.MainStateEvent.GetBlogPostsEvent
import com.example.android.mvi.ui.main.state.MainStateEvent.GetUserEvent
import com.example.android.mvi.ui.main.state.MainStateEvent.None
import com.example.android.mvi.util.AbsentLiveData

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    //getter
    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> = Transformations
        .switchMap(_stateEvent){stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> {
        when (stateEvent) {
            is GetBlogPostsEvent -> {
                return AbsentLiveData.create()
            }

            is GetUserEvent -> {
                return AbsentLiveData.create()
            }

            is None -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPost: List<Blogs>){
        val update = getCurrentViewStateOrNew()
        update.blogPost = blogPost
        _viewState.value = update

    }

    fun setUser(user: User){
        val update = getCurrentViewStateOrNew()
        update.users = user
        _viewState.value = update

    }

    fun getCurrentViewStateOrNew(): MainViewState{
        val value = viewState.value?.let {
            it
        }?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent){
        _stateEvent.value = event
    }

}