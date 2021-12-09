package com.uzlov.valitova.justcargo.repo.net

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Request

interface IRequestsRepository {
    fun getRequests() : LiveData<List<Request>>
    fun getRequest(id: Int) : LiveData<Request?>
    fun getRequestsWithStatus(id: Int) : LiveData<List<Request>>
    fun removeRequest(id: Int)
    fun putRequest(request: Request)
}