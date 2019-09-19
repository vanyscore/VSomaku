package com.example.vsomaku.repos

import io.reactivex.disposables.CompositeDisposable

abstract class BaseRepo {
    protected val disposable = CompositeDisposable()

    fun destroy() {
        disposable.dispose()
    }
}