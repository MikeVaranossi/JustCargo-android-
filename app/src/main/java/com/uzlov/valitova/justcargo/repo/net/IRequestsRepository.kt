package com.uzlov.valitova.justcargo.repo.net

import com.uzlov.valitova.justcargo.model.entities.Request

interface IRequestsRepository {
    fun getRequests() : List<Request>
    fun getRequest(id: Long) : Request?
    fun removeRequest(id: Long)
    fun putRequest(request: Request)
}