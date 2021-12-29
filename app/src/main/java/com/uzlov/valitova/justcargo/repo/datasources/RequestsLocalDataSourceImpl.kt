package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.local.MyRequestLocal
import com.uzlov.valitova.justcargo.repo.local.room.FavoriteRequestDao
import com.uzlov.valitova.justcargo.repo.local.room.MyRequestDao
import javax.inject.Inject

class RequestsLocalDataSourceImpl @Inject constructor(var daoFavourite: FavoriteRequestDao, var daoLocal: MyRequestDao): IRequestsLocalDataSource {

    // Работа с избранными заявками
    override fun getFavRequests(): LiveData<List<FavoriteRequestLocal>> = daoFavourite.getFavoriteRequests()
    override fun getFavIDsRequests(): List<Long> = daoFavourite.getIDsFavoriteRequests()
    override fun getFavRequest(id: Long): LiveData<FavoriteRequestLocal?> = daoFavourite.getFavoriteRequest(id)
    override suspend fun removeFavRequest(request: FavoriteRequestLocal) = daoFavourite.removeRequest(request)
    override suspend fun putFavRequest(request: FavoriteRequestLocal) = daoFavourite.insertRequest(request)
    override suspend fun updateFavRequest(request: FavoriteRequestLocal) = daoFavourite.updateRequest(request)

    // Работа с моими заявками
    override fun getMyRequests(): LiveData<List<MyRequestLocal>> = daoLocal.getMyRequests()
    override fun getMyRequest(id: Long): LiveData<MyRequestLocal?> = daoLocal.getMyRequest(id)
    override suspend fun removeMyRequest(request: MyRequestLocal) =
        daoLocal.removeMyRequest(request)

    override suspend fun removeMyRequest(id: Long) = daoLocal.removeMyRequest(id)

    override suspend fun putMyRequest(requests: List<MyRequestLocal>) =
        daoLocal.insertMyRequest(requests)

    override suspend fun updateMyRequest(request: MyRequestLocal) =
        daoLocal.updateMyRequest(request)
}