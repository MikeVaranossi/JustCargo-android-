package com.uzlov.valitova.justcargo.repo.repositories

import com.uzlov.valitova.justcargo.model.entities.Request
import com.uzlov.valitova.justcargo.repo.datasources.RequestsRemoteDataSource
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import javax.inject.Inject

class RequestRepositoryImpl @Inject constructor(var remoteDataSource: RequestsRemoteDataSource) :
    IRequestsRepository {
    override fun getRequests(): List<Request> = remoteDataSource.getRequests()

    override fun getRequest(id: Long): Request? = remoteDataSource.getRequest(id)

    override fun removeRequest(id: Long) = remoteDataSource.removeRequest(id)

    override fun putRequest(request: Request) = remoteDataSource.putRequest(request)
}