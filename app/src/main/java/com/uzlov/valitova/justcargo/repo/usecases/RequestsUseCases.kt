package com.uzlov.valitova.justcargo.repo.usecases

import com.uzlov.valitova.justcargo.model.entities.Request
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import javax.inject.Inject

class RequestsUseCases @Inject constructor(var requestRepository: IRequestsRepository) {
    suspend fun getRequests() = requestRepository.getRequests()
    suspend fun getRequests(id: Long) = requestRepository.getRequest(id)
    suspend fun removeRequests(id: Long) = requestRepository.removeRequest(id)
    suspend fun putRequest(request: Request) = requestRepository.putRequest(request)
}
