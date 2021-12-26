package com.uzlov.valitova.justcargo.repo.net

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Request

interface IRequestsRepository {
    fun getRequests(): LiveData<List<Request>>
    fun getRequest(id: Long): LiveData<Request?>
    fun getRequestsWithStatus(id: Int): LiveData<List<Request>>
    fun getRequestsWithPhone(phone: String): LiveData<List<Request>>
    fun getRequestsWithUserID(id: Int): LiveData<List<Request>>
    fun removeRequest(id: Long)
    fun putRequest(request: Request)
    fun searchRequest(
        from: String,
        to: String,
        dateTimeStart: Long,
    ): LiveData<List<Request>>

    fun searchRequest(from: String, to: String): LiveData<List<Request>>
}