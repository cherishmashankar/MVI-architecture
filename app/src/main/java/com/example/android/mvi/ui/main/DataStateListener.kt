package com.example.android.mvi.ui.main

import com.example.android.mvi.util.DataState

interface DataStateListener {

    fun onDataStateChangeListener(dataState: DataState<*>?)
}
