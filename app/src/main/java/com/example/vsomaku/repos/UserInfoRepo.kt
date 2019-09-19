package com.example.vsomaku.repos

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class UserInfoRepo(private val api : SomakuApi) : BaseRepo() {
    fun loadAlbumsData(consumer : Consumer<Pair<List<Album>, List<Photo>>>, errorConsumer : Consumer<Throwable>, userId : Int) {
        disposable.add(api.getAlbums(userId)
            .map { response ->
                response.body()
            }
            .toObservable()
            .map {
                it
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ albums ->
                getPhotos(Consumer { photos ->
                    consumer.accept(albums to photos)
                }, errorConsumer, albums)
            }, {
                Log.d(DEBUG_TAG, it.localizedMessage)
            }))
    }

    private fun getPhotos(consumer : Consumer<List<Photo>>, errorConsumer : Consumer<Throwable>,
                          albums : List<Album>) {
        val flow : ArrayList<Flowable<Response<List<Photo>>>> = arrayListOf()
        for (album : Album in albums) flow.add(api.getPhotos(album.id))
        val flows : Flowable<Response<List<Photo>>> = Flowable.merge(flow)

        disposable.add(flows
            .map {response ->
                response.body()
            }
            .toObservable()
            .flatMapIterable {
                it
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer, errorConsumer))
    }
}