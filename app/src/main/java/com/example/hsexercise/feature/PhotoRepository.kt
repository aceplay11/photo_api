package com.example.hsexercise.feature

import android.app.Application
import com.example.hsexercise.common.DatabaseProvider
import com.example.hsexercise.common.NetworkProvider
import com.example.hsexercise.feature.database.FeatureModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PhotoRepository(app: Application) {
    private lateinit var subscription: Disposable
    private val retrofit = NetworkProvider.provideRestClient()
    private val webService: WebService =
        retrofit.createRetrofitAdapter().create(WebService::class.java)
    private val featureTableDao =
        DatabaseProvider.provideRoomDatabase(app).featureTableDao()

    fun loadPhotos(observer: DisposableObserver<FeatureModel>) {
        subscription = Observable.fromCallable { featureTableDao.getAll() }
            .concatMap {
                if (it == it.isEmpty)
                    webService.getPhotosList().concatMap {
                            featureModel ->
                        featureTableDao.insert(featureModel)
                        Observable.just(featureModel)
                    }

                else
                    Observable.just(it)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }


//    private fun getPhotosFromDb(): Observable<List<FeatureModel>> {
//        return database.featureTableDao().getAll().filter { it.isNotEmpty() }
//            .toObservable()
//    }
//
//    private fun getPhotosFromWeb(): Observable<List<FeatureModel>> {
//        return webService.getPhotosList().doOnNext {
//            saveToDb(it)
//        }
//    }
//
//    private fun saveToDb(photos: List<FeatureModel>) {
//        Observable.fromCallable { database.featureTableDao().insertAll(photos) }
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe()
//    }
}
