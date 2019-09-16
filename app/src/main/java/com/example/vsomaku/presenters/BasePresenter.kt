package com.example.vsomaku.presenters

abstract class BasePresenter<V> {
    protected var view : V? = null

    fun bindView(view : V) {
        this.view = view
    }

    fun unbindView() {
        this.view = null
    }
}