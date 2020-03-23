package com.example.hsexercise.feature

import android.app.Application
import androidx.lifecycle.*
import com.example.hsexercise.feature.database.FeatureModel
import io.reactivex.observers.DisposableObserver


class FeatureViewModel : ViewModel() {

    private lateinit var repository: PhotoRepository

    private val photoResults = MutableLiveData<FeatureModel>()
    private val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()
    private lateinit var disposableObserver: DisposableObserver<FeatureModel>

    class Factory :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = FeatureViewModel() as T
    }

    fun getPhotosResults(): LiveData<FeatureModel>{
        return photoResults
    }

    fun getErrorMessage(): LiveData<String>{
        return errorMessage
    }

    fun isLoading(): LiveData<Int>{
        return loadingVisibility
    }

    fun getData(){

        disposableObserver = object: DisposableObserver<FeatureModel>(){
            override fun onComplete() {
                loadingVisibility.postValue(0)
            }

            override fun onNext(t: FeatureModel) {
                photoResults.postValue(t)
                loadingVisibility.postValue(1)
            }

            override fun onError(e: Throwable) {
                errorMessage.postValue(e.message)
            }

        }
        repository.loadPhotos(disposableObserver)
    }

    fun dispose(){
        if (!disposableObserver.isDisposed and (disposableObserver != null))
            disposableObserver.dispose()
    }
    fun setRepo(application: Application){
        repository = PhotoRepository(application)
    }
}

