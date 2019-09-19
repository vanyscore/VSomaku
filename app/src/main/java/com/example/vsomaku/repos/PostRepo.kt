package com.example.vsomaku.repos

import com.example.vsomaku.ApiHelper
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.data.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostRepo(private val api: SomakuApi) : BaseRepo() {

    fun getPosts( consumer: Consumer<List<Post>>, errorConsumer: Consumer<Throwable>){
        disposable.add(ApiHelper.apiInstance.getPosts()
            .map { response ->
                response.body()
            }
            .toObservable()
            .flatMapIterable {
                it
            }
            .map {
                Post(
                    it.userId,
                    it.id,
                    it.title?.let { title ->
                        title[0].toUpperCase() + title.substring(1)
                    },
                    it.description?.let { description ->
                        description[0].toUpperCase() + description.substring(1).replace(
                            "\n",
                            ""
                        )
                    }
                )
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer, errorConsumer))
    }
}