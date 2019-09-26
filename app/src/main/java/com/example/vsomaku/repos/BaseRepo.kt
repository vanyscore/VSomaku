package com.example.vsomaku.repos

import android.content.Context
import com.example.vsomaku.App
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.activities.MainActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseRepo(protected val api : SomakuApi) {

    protected val disposable = CompositeDisposable()

    fun destroy() {
        disposable.dispose()
    }

    override fun toString(): String {

        return this.javaClass.name
    }
}