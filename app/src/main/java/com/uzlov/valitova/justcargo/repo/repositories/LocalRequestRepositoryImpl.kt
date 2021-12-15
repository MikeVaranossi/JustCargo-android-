package com.uzlov.valitova.justcargo.repo.repositories

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.repo.datasources.IRequestsLocalDataSource
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository

class LocalRequestRepositoryImpl(var localDataSource: IRequestsLocalDataSource):ILocalRepository {
    override suspend fun getRequests(): LiveData<List<FavoriteRequestLocal>> {
        return localDataSource.getRequests()
    }

    override suspend fun getRequest(id: Long): LiveData<FavoriteRequestLocal?> {
        return localDataSource.getRequest(id)
    }

    override suspend fun removeRequest(request: FavoriteRequestLocal) {
        localDataSource.removeRequest(request)
    }

    override suspend fun putRequest(request: FavoriteRequestLocal) {
        localDataSource.putRequest(request)
    }

    override suspend fun updateRequest(request: FavoriteRequestLocal) {
        localDataSource.updateRequest(request)
    }

}