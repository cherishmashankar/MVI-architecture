package com.example.android.mvi.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.mvi.R
import com.example.android.mvi.ui.main.state.MainStateEvent
import java.lang.ClassCastException

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var dataStateHandler: DataStateListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run{
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid Activity")

        subscribeObservers()
    }

    fun subscribeObservers(){

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->
            println("DEBUG: DataState: $dataState")

            //handles loading and message
            dataStateHandler.onDataStateChangeListener(dataState)


            //handles data
            dataState.data?.let { mainViewState ->
                mainViewState.blogPost?.let{blogPosts ->
                    //set blogPosts
                    viewModel.setBlogListData(blogPosts)

                }
                mainViewState.users?.let{user ->
                    //set user
                    viewModel.setUser(user)
                }
                
            }




            viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
                viewState.blogPost?.let {
                    println("DEBUG: Setting blog posts to RecyclerView: ${it}")
                }

                viewState.users?.let {
                    println("DEBUG: Setting users: ${it}")
                }
            })


        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_get_user -> triggerGetUserEvent()

            R.id.action_get_blogs -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())

    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            dataStateHandler = context as DataStateListener
        }catch(e: ClassCastException){
            println("Debug: $context must implement DataStateListener")
        }
    }
}