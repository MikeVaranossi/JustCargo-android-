package com.uzlov.valitova.justcargo.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.repo.datasources.IRequestsLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

// ViewModel для взаимодествия с данными из БД (заявками добавленным ив избранное)
class FavoritesRequestsViewModel @Inject constructor(var favIRequestsRepository: IRequestsLocalDataSource) : BaseViewModel() {

    fun getRequests() : LiveData<List<FavoriteRequestLocal>> {
        return favIRequestsRepository.getRequests()
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