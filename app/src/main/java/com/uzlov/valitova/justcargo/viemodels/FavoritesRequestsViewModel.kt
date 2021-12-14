package com.uzlov.valitova.justcargo.viemodels

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.repo.datasources.IRequestsLocalDataSource
import javax.inject.Inject

// ViewModel для взаимодествия с данными из БД (заявками добавленным ив избранное)
class FavoritesRequestsViewModel @Inject constructor(var favIRequestsRepository: IRequestsLocalDataSource) : BaseViewModel() {

    fun getRequests() : LiveData<List<FavoriteRequestLocal>> = favIRequestsRepository.getRequests()
    fun putRequest(request: FavoriteRequestLocal)  = favIRequestsRepository.putRequest(request)
    fun removeRequest(request: FavoriteRequestLocal)  = favIRequestsRepository.removeRequest(request)
}