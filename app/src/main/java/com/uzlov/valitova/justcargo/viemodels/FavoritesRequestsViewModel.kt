package com.uzlov.valitova.justcargo.viemodels

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import javax.inject.Inject

// ViewModel для взаимодествия с данными из БД (заявками добавленным ив избранное)
class FavoritesRequestsViewModel @Inject constructor(var favIRequestsRepository: IRequestsRepository) : BaseViewModel() {

    fun getRequests() : LiveData<List<Request>> = favIRequestsRepository.getRequests()
    fun getRequestsByID(id: Int) : LiveData<Request?> = favIRequestsRepository.getRequest(id)
    fun putRequest(request: Request)  = favIRequestsRepository.putRequest(request)
    fun removeRequest(id: Int)  = favIRequestsRepository.removeRequest(id)
}