package com.uzlov.valitova.justcargo.repo.repositories

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.local.MyRequestLocal
import com.uzlov.valitova.justcargo.repo.datasources.IRequestsLocalDataSource
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository

class LocalRequestRepositoryImpl(var localDataSource: IRequestsLocalDataSource) : ILocalRepository {

    // Работа с избранными заявками
    override suspend fun getFavRequests(): LiveData<List<FavoriteRequestLocal>> =
        localDataSource.getFavRequests()
    override suspend fun getFavIDsRequests(): List<Long> = localDataSource.getFavIDsRequests()
    override suspend fun getFavRequest(id: Long): LiveData<FavoriteRequestLocal?> =
        localDataSource.getFavRequest(id)
    override suspend fun removeFavRequest(request: FavoriteRequestLocal) =
        localDataSource.removeFavRequest(request)
    override suspend fun putFavRequest(request: FavoriteRequestLocal) =
        localDataSource.putFavRequest(request)
    override suspend fun updateFavRequest(request: FavoriteRequestLocal) =
        localDataSource.updateFavRequest(request)


    // Работа с моими заявками
    override suspend fun getMyRequests(): LiveData<List<MyRequestLocal>> =
        localDataSource.getMyRequests()
    override suspend fun getMyRequest(id: Long): LiveData<MyRequestLocal?> =
        localDataSource.getMyRequest(id)
    override suspend fun removeMyRequest(request: MyRequestLocal) =
        localDataSource.removeMyRequest(request)
    override suspend fun putMyRequest(requests: List<MyRequestLocal>) =
        localDataSource.putMyRequest(requests)
    override suspend fun updateMyRequest(request: MyRequestLocal) =
        localDataSource.updateMyRequest(request)
}