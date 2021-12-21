package com.uzlov.valitova.justcargo.repo.local

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal

interface ILocalRepository {
    suspend fun getRequests() : LiveData<List<FavoriteRequestLocal>>
    suspend fun getIDsRequests() : List<Long>
    suspend fun getRequest(id: Long) : LiveData<FavoriteRequestLocal?>
    suspend fun removeRequest(request: FavoriteRequestLocal)
    suspend fun putRequest(request: FavoriteRequestLocal)
    suspend fun updateRequest(request: FavoriteRequestLocal)
}