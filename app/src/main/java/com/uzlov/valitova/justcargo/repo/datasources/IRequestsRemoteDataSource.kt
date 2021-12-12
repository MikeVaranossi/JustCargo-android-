package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Request

interface IRequestsRemoteDataSource {
    fun getRequests() : LiveData<List<Request>>
    fun getRequest(id: Int) :  LiveData<Request?>
    fun removeRequest(id: Int)
    fun putRequest(request: Request)
    fun getRequestsWithStatus(id: Int): LiveData<List<Request>>
    fun getRequestsWithPhone(phone: String): LiveData<List<Request>>
    fun getRequestsWithUserID(id: Int): LiveData<List<Request>>
}