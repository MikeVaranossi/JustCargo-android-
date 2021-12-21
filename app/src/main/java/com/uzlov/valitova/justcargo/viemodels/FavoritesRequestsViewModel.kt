package com.uzlov.valitova.justcargo.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.repo.datasources.IRequestsLocalDataSource
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// ViewModel для взаимодествия с данными из БД (заявками добавленным ив избранное)
class FavoritesRequestsViewModel @Inject constructor(var favIRequestsRepository: ILocalRepository) : BaseViewModel() {

    fun getRequests() : LiveData<List<FavoriteRequestLocal>> = runBlocking{
        val mDeferredResult = async {
            favIRequestsRepository.getRequests()
        }
            mDeferredResult.await()
    }

    fun putRequest(request: FavoriteRequestLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favIRequestsRepository.putRequest(request)
            } catch (e:Exception) {
                Log.e("database", "impossible to put favorite request to DB")
            }
        }
    }
    fun removeRequest(request: FavoriteRequestLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favIRequestsRepository.removeRequest(request)
            } catch (e:Exception) {
                Log.e("database", "impossible to remove favorite request from DB")
            }
        }
    }
}