package com.uzlov.valitova.justcargo.repo.net

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.model.entities.Request

interface IRequestsRepository {
    fun getRequests() : LiveData<List<Request>>
    fun getRequest(id: String) : LiveData<Request?>
    fun removeRequest(id: String)
    fun putRequest(request: Request)
}