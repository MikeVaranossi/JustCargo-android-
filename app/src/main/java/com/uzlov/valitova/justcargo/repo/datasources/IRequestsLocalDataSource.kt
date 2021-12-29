package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.local.MyRequestLocal

interface IRequestsLocalDataSource {

    //Работа с избранными заявками
    fun getFavRequests() : LiveData<List<FavoriteRequestLocal>>
    fun getFavIDsRequests() : List<Long>
    fun getFavRequest(id: Long) : LiveData<FavoriteRequestLocal?>
    suspend fun removeFavRequest(request: FavoriteRequestLocal)
    suspend fun putFavRequest(request: FavoriteRequestLocal)
    suspend fun updateFavRequest(request: FavoriteRequestLocal)

    //Работа с моими заявками
    fun getMyRequests() : LiveData<List<MyRequestLocal>>
    fun getMyRequest(id: Long) : LiveData<MyRequestLocal?>
    suspend fun removeAllMyRequests()
    suspend fun removeMyRequest(request: MyRequestLocal)
    suspend fun removeMyRequest(id: Long)
    suspend fun putMyRequest(requests: List<MyRequestLocal>)
    suspend fun updateMyRequest(request: MyRequestLocal)
}