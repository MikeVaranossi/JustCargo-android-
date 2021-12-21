package com.uzlov.valitova.justcargo.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.repo.datasources.IRequestsLocalDataSource
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

// ViewModel для взаимодествия с данными из БД (заявками добавленным ив избранное)
class FavoritesRequestsViewModel @Inject constructor(private var favIRequestsRepository: RequestsUseCases) :
    BaseViewModel() {

    fun getRequests(): LiveData<List<FavoriteRequestLocal>> = runBlocking {
        val mDeferredResult = async {
            favIRequestsRepository.getFavouritesRequests()
        }
        mDeferredResult.await()
    }

    fun putRequest(request: FavoriteRequestLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favIRequestsRepository.putFavouriteRequest(request)
            } catch (e: Exception) {
                Log.e("database", "impossible to put favorite request to DB")
            }
        }
    }

    fun removeRequest(request: FavoriteRequestLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favIRequestsRepository.removeFavouriteRequest(request)
            } catch (e: Exception) {
                Log.e("database", "impossible to remove favorite request from DB")
            }
        }
    }

    // корутины работают во viewmodel с viewModelScope
    fun getIDList(): LiveData<List<Long>> {
        val result = MutableLiveData<List<Long>>()

        // сначала отменяем предыдущий запрос
        viewModelScope.coroutineContext.cancelChildren()

        // отправляем новый
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(favIRequestsRepository.getFavouritesIDs())
        }
        return result
    }
}