package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.model.entities.Request

interface RequestsRemoteDataSource {
    fun getRequests() : LiveData<List<Request>>
    fun getRequest(id: String) :  LiveData<Request?>
    fun removeRequest(id: String)
    fun putRequest(request: Request)
}