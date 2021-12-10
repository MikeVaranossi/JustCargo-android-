package com.uzlov.valitova.justcargo.repo.local

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Request

interface ILocalRepository {
    fun getRequests() : LiveData<List<FavoriteRequestLocal>>
    fun getRequest(id: Long) : LiveData<FavoriteRequestLocal?>
    fun removeRequest(id: Long)
    fun putRequest(request: FavoriteRequestLocal)
}