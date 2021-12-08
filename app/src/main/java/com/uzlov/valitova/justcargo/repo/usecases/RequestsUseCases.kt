package com.uzlov.valitova.justcargo.repo.usecases

import com.uzlov.valitova.justcargo.model.entities.Request
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import javax.inject.Inject

class RequestsUseCases @Inject constructor(var requestRepository: IRequestsRepository) {
    fun getRequests() = requestRepository.getRequests()
    fun getRequests(id: String) = requestRepository.getRequest(id)
    fun removeRequests(id: String) = requestRepository.removeRequest(id)
    fun putRequest(request: Request) = requestRepository.putRequest(request)
}
