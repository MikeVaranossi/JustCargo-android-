package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal

interface IRequestsLocalDataSource {
    fun getRequests() : LiveData<List<FavoriteRequestLocal>>
    fun getRequest(id: Long) : LiveData<FavoriteRequestLocal?>
    fun removeRequest(request: FavoriteRequestLocal)
    fun putRequest(request: FavoriteRequestLocal)
    fun updateRequest(request: FavoriteRequestLocal)
}