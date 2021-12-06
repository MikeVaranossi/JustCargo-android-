package com.uzlov.valitova.justcargo.viemodels

import com.uzlov.valitova.justcargo.model.entities.Request
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import javax.inject.Inject

class RequestsViewModel @Inject constructor(private var requestsUseCases: RequestsUseCases?)  : BaseViewModel() {

    fun getRequests() = requestsUseCases?.getRequests()

    fun getRequest(id: String) = requestsUseCases?.getRequests(id)

    fun addRequest(request: Request) = requestsUseCases?.putRequest(request)

    fun removeRequest(id: String) = requestsUseCases?.removeRequests(id)
}