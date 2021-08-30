package com.example.android.mvi.ui.main.state

import com.example.android.mvi.model.Blogs
import com.example.android.mvi.model.User

data class MainViewState(
    var blogPost: List<Blogs>? = null,
    var users: User? = null
) {
}