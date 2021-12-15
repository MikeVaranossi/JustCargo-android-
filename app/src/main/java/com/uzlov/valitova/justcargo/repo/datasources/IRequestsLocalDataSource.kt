package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal

interface IRequestsLocalDataSource {
    fun getRequests() : LiveData<List<FavoriteRequestLocal>>
    fun getRequest(id: Long) : LiveData<FavoriteRequestLocal?>
    suspend fun removeRequest(request: FavoriteRequestLocal)
    suspend fun putRequest(request: FavoriteRequestLocal)
    suspend fun updateRequest(request: FavoriteRequestLocal)
}