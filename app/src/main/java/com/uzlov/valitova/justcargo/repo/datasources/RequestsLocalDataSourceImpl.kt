package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.repo.local.room.FavoriteRequestDao
import javax.inject.Inject

class RequestsLocalDataSourceImpl @Inject constructor(var dao: FavoriteRequestDao): IRequestsLocalDataSource {

    override fun getRequests(): LiveData<List<FavoriteRequestLocal>> {
        return dao.getFavoriteRequests()
    }

    override fun getIDsRequests(): List<Long> {
        return dao.getIDsFavoriteRequests()
    }

    override fun getRequest(id: Long): LiveData<FavoriteRequestLocal?> {
        return dao.getFavoriteRequest(id)
    }

    override suspend fun removeRequest(request: FavoriteRequestLocal) {
        dao.removeRequest(request)
    }

    override suspend fun putRequest(request: FavoriteRequestLocal) {
        dao.insertRequest(request)
    }

    override suspend fun updateRequest(request: FavoriteRequestLocal) {
        dao.updateRequest(request)
    }
}