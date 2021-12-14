package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.repo.local.room.RoomFavoritesRequestDB
import com.uzlov.valitova.justcargo.app.App
import com.uzlov.valitova.justcargo.repo.local.room.FavoriteRequestDao
import javax.inject.Inject
import javax.inject.Named

class RequestsLocalDataSourceImpl @Inject constructor(var dao: FavoriteRequestDao): IRequestsLocalDataSource {

    override fun getRequests(): LiveData<List<FavoriteRequestLocal>> {
        return dao.getFavoriteRequests()
    }

    override fun getRequest(id: Long): LiveData<FavoriteRequestLocal?> {
        return dao.getFavoriteRequest(id)
    }

    override fun removeRequest(request: FavoriteRequestLocal) {
        dao.removeRequest(request)
    }

    override fun putRequest(request: FavoriteRequestLocal) {
        dao.insertRequest(request)
    }

    override fun updateRequest(request: FavoriteRequestLocal) {
        dao.updateRequest(request)
    }
}