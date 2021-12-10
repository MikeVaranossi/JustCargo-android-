package com.uzlov.valitova.justcargo.viemodels

import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import javax.inject.Inject

class RequestsViewModel @Inject constructor(private var requestsUseCases: RequestsUseCases?)  : BaseViewModel() {

    fun getRequests() = requestsUseCases?.getRequests()

    fun getRequest(id: Int) = requestsUseCases?.getRequests(id)

    fun addRequest(request: Request) = requestsUseCases?.putRequest(request)

    fun removeRequest(id: Int) = requestsUseCases?.removeRequests(id)
}