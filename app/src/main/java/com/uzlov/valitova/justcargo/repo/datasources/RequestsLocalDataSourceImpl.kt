package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.repo.local.room.RoomFavoritesRequestDB
import com.uzlov.valitova.justcargo.app.App

class RequestsLocalDataSourceImpl: IRequestsLocalDataSource {
    private val db = Room.databaseBuilder(
        App.applicationContext(),
        RoomFavoritesRequestDB::class.java, "favoriteRequest"
    ).build()
    private val resultAll = MutableLiveData<List<FavoriteRequestLocal>>()
    private val resultRequest = MutableLiveData<FavoriteRequestLocal>()


    override fun getRequests(): LiveData<List<FavoriteRequestLocal>> {
        resultAll.value = db.favoriteRequestDao.getFavoriteRequests()
        return resultAll
    }

    override fun getRequest(id: Long): LiveData<FavoriteRequestLocal?> {
        resultRequest.value = db.favoriteRequestDao.getFavoriteRequest(id)
        return resultRequest
    }

    override fun removeRequest(id: Long) {
//        db.favoriteRequestDao.removeRequest(id)
    }

    override fun putRequest(request: FavoriteRequestLocal) {
        db.favoriteRequestDao.insertRequest(request)
    }
}