package com.uzlov.valitova.justcargo.repo.datasources

import com.uzlov.valitova.justcargo.model.entities.Request

interface RequestsRemoteDataSource {
    fun getRequests() : List<Request>
    fun getRequest(id: Long) : Request?
    fun removeRequest(id: Long)
    fun putRequest(request: Request)
}