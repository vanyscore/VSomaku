package com.example.vsomaku.repos

import com.example.vsomaku.Components
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.daos.PostDao
import com.example.vsomaku.databases.SomakuDatabase
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseRepo {

    protected val disposable = CompositeDisposable()

    @Inject
    protected lateinit var api : SomakuApi

    init {
        Components.APP_COMPONENT?.injectRepo(this)
    }

    fun destroy() {
        disposable.dispose()
    }

    override fun toString(): String {

        return this.javaClass.name
    }
}