package com.uzlov.valitova.justcargo.repo.datasources

import com.uzlov.valitova.justcargo.model.entities.Request

class RequestsRemoteDataSourceImpl : RequestsRemoteDataSource {
    override fun getRequests(): List<Request> {
        return emptyList()
    }

    override fun getRequest(id: Long): Request? {
        return null
    }

    override fun removeRequest(id: Long) {

    }

    override fun putRequest(request: Request) {

    }
}