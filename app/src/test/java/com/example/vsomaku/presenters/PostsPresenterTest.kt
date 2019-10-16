package com.example.vsomaku.presenters

import android.content.Context
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.views.PostsView
import com.example.vsomaku.presenters.views.`PostsView$$State`
import com.example.vsomaku.repos.PostRepo
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.functions.Consumer
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`

class PostsPresenterTest : BasePresenterTest() {

    @Mock
    private lateinit var repo : PostRepo
    @Mock
    private lateinit var context : Context
    @Mock
    private lateinit var view : PostsView
    @Mock
    private lateinit var viewState : `PostsView$$State`

    private lateinit var presenter : PostsPresenter

    @Captor
    private lateinit var successCaptor : ArgumentCaptor<Consumer<List<Post>>>
    @Captor
    private lateinit var errorCaptor : ArgumentCaptor<Consumer<Throwable>>

    @Before
    fun initializePresenter() {
        presenter = PostsPresenter(repo, context)
        presenter.setViewState(viewState)
        presenter.attachView(view)
    }

    @Before
    fun provideReturnValues() {
        `when`(repo.loadPosts(capture(successCaptor), capture(errorCaptor))).then {
            successCaptor.value.accept(emptyList())
        }

        `when`(repo.loadPosts(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), capture(successCaptor), capture(errorCaptor))).then {
            successCaptor.value.accept(emptyList())
        }
    }

    @Test
    fun getPosts() {
        presenter.getPosts()

        verify(viewState).bindPagedList(ArgumentMatchers.any())
        verify(viewState).bindPosts(emptyList())
    }
}