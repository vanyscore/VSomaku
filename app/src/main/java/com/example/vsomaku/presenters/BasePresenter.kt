package com.example.vsomaku.presenters

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V> {
    protected var view : V? = null
    protected val disposable = CompositeDisposable()

    fun bindView(view : V) {
        this.view = view
    }

    fun unbindView() {
        this.view = null
    }

    fun destroy(){
        disposable.dispose()
        disposable.clear()
    }
}