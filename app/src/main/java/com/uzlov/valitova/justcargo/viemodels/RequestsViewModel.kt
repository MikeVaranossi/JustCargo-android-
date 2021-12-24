package com.uzlov.valitova.justcargo.viemodels

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RequestsViewModel @Inject constructor(private var requestsUseCases: RequestsUseCases?) :
    BaseViewModel() {

    // возвращает все заявки
    fun getRequests() = runBlocking {
        val mDeferredResult = async {
            requestsUseCases?.getRequests()
        }
        mDeferredResult.await()
    }

    // возвращает заявку с указанным ID
    fun getRequest(id: Long) = requestsUseCases?.getRequests(id)

    // добавляет заявку
    fun addRequest(request: Request) = requestsUseCases?.putRequest(request)

    // удаляет заявку
    fun removeRequest(id: Long) = requestsUseCases?.removeRequests(id)

    // возвращает все заявки с указанным статусом
    fun getRequestsWithStatus(status: Int) = requestsUseCases?.getRequestsWithStatus(status)

    // возвращает все заявки с указанным телефоном
    fun getRequestsWithPhone(phone: String) = requestsUseCases?.getRequestsWithPhone(phone)

    // возвращает все заявки с указанным id пользователя (создателя)
    fun getRequestsWithUserID(id: Int) = requestsUseCases?.getRequestsWithUserID(id)

    fun searchRequest(
        from: String,
        to: String,
        dateTimeStart: Long,
        dateTimeEnd: Long,
    ): LiveData<List<Request>>? = requestsUseCases?.searchRequest(
        from,
        to,
        dateTimeStart,
        dateTimeEnd
    )
}