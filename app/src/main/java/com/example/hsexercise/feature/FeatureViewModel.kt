package com.example.hsexercise.feature

import android.app.Application
import androidx.lifecycle.*
import com.example.hsexercise.feature.database.FeatureModel
import io.reactivex.observers.DisposableObserver


class FeatureViewModel : AndroidViewModel(Application()) {

    private var repository: PhotoRepository = PhotoRepository(getApplication())

    private val photoResults = MutableLiveData<List<FeatureModel>>()
    private val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()
    private lateinit var disposableObserver: DisposableObserver<List<FeatureModel>>

    class Factory :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = FeatureViewModel() as T
    }

    fun getPhotosResults(): LiveData<List<FeatureModel>>{
        return photoResults
    }

    fun getErrorMessage(): LiveData<String>{
        return errorMessage
    }

    fun getLoading(): LiveData<Int>{
        return loadingVisibility
    }

    fun getData(){

        disposableObserver = object: DisposableObserver<List<FeatureModel>>(){
            override fun onComplete() {
                loadingVisibility.postValue(0)
            }

            override fun onNext(t: List<FeatureModel>) {
                photoResults.postValue(t)
                loadingVisibility.postValue(1)
            }

            override fun onError(e: Throwable) {
                errorMessage.postValue(e.message)
            }

        }
        repository.loadPhotos()
    }

    fun dispose(){
        if (!disposableObserver.isDisposed and (disposableObserver != null))
            disposableObserver.dispose()
    }
}

