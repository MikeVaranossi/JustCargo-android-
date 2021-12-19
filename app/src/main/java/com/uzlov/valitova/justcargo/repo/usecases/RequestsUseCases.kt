package com.uzlov.valitova.justcargo.repo.usecases

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import javax.inject.Inject

class RequestsUseCases @Inject constructor(
    var requestRepository: IRequestsRepository,
    var localRequestRepository: ILocalRepository,
) {
    suspend fun getRequests(): LiveData<List<Request>> {
        val requests = requestRepository.getRequests()
        val idList = localRequestRepository.getIDList()
        val requestList = requests.value
        for (request in requestList!!) {
            if (request.id in idList) {
                request.isFavourites=true
            }
        }
        return requests
    }
    fun getRequests(id: Int) = requestRepository.getRequest(id)
    fun removeRequests(id: Int) = requestRepository.removeRequest(id)
    fun putRequest(request: Request) = requestRepository.putRequest(request)
    fun getRequestsWithStatus(id: Int) = requestRepository.getRequestsWithStatus(id)
    fun getRequestsWithPhone(phone: String) = requestRepository.getRequestsWithPhone(phone)
    fun getRequestsWithUserID(id: Int) = requestRepository.getRequestsWithUserID(id)
}
