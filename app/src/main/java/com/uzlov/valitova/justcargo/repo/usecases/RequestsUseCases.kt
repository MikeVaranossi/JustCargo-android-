package com.uzlov.valitova.justcargo.repo.usecases

import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import javax.inject.Inject

class RequestsUseCases @Inject constructor(var requestRepository: IRequestsRepository) {
    fun getRequests() = requestRepository.getRequests()
    fun getRequests(id: Int) = requestRepository.getRequest(id)
    fun removeRequests(id: Int) = requestRepository.removeRequest(id)
    fun putRequest(request: Request) = requestRepository.putRequest(request)
    fun getRequestsWithStatus(id: Int) = requestRepository.getRequestsWithStatus(id)
    fun getRequestsWithPhone(phone: String) = requestRepository.getRequestsWithPhone(phone)
    fun getRequestsWithUserID(id: Int) = requestRepository.getRequestsWithUserID(id)
}
