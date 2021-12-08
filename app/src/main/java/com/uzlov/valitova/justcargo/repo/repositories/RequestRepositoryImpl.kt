package com.uzlov.valitova.justcargo.repo.repositories

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.datasources.IRequestsRemoteDataSource
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import javax.inject.Inject

class RequestRepositoryImpl @Inject constructor(var remoteDataSource: IRequestsRemoteDataSource) :
    IRequestsRepository {
    override fun getRequests(): LiveData<List<Request>> = remoteDataSource.getRequests()

    override fun getRequest(id: String): LiveData<Request?> = remoteDataSource.getRequest(id)

    override fun removeRequest(id: String) = remoteDataSource.removeRequest(id)

    override fun putRequest(request: Request) = remoteDataSource.putRequest(request)
}