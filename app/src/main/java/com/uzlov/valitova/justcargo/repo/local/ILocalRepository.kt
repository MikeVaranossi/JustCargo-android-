package com.uzlov.valitova.justcargo.repo.local

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.local.MyRequestLocal

interface ILocalRepository {
    // Работа с избранным
    suspend fun getFavRequests() : LiveData<List<FavoriteRequestLocal>>
    suspend fun getFavIDsRequests() : List<Long>
    suspend fun getFavRequest(id: Long) : LiveData<FavoriteRequestLocal?>
    suspend fun removeFavRequest(request: FavoriteRequestLocal)
    suspend fun putFavRequest(request: FavoriteRequestLocal)
    suspend fun updateFavRequest(request: FavoriteRequestLocal)

    // Работа с моими заявками
    suspend fun getMyRequests() : LiveData<List<MyRequestLocal>>
    suspend fun getMyRequest(id: Long) : LiveData<MyRequestLocal?>
    suspend fun removeMyRequest(request: MyRequestLocal)
    suspend fun putMyRequest(request: MyRequestLocal)
    suspend fun updateMyRequest(request: MyRequestLocal)
}