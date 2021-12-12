package com.uzlov.valitova.justcargo.viemodels

import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import javax.inject.Inject

class RequestsViewModel @Inject constructor(private var requestsUseCases: RequestsUseCases?)  : BaseViewModel() {

    // возвращает все заявки
    fun getRequests() = requestsUseCases?.getRequests()

    // возвращает заявку с указанным ID
    fun getRequest(id: Int) = requestsUseCases?.getRequests(id)

    // добавляет заявку
    fun addRequest(request: Request) = requestsUseCases?.putRequest(request)

    // удаляет заявку
    fun removeRequest(id: Int) = requestsUseCases?.removeRequests(id)

    // возвращает все заявки с указанным статусом
    fun getRequestsWithStatus(status: Int) = requestsUseCases?.getRequestsWithStatus(status)

    // возвращает все заявки с указанным телефоном
    fun getRequestsWithPhone(phone: String) = requestsUseCases?.getRequestsWithPhone(phone)

    // возвращает все заявки с указанным id пользователя (создателя)
    fun getRequestsWithUserID(id: Int) = requestsUseCases?.getRequestsWithUserID(id)
}