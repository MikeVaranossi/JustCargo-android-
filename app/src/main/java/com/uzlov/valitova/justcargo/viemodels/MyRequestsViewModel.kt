package com.uzlov.valitova.justcargo.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.uzlov.valitova.justcargo.data.local.MyRequestLocal
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.repositories.RequestRepositoryImpl
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MyRequestsViewModel @Inject constructor(private var requestsUseCases: RequestsUseCases?) :
    BaseViewModel() {

    fun getMyRequests(): LiveData<List<MyRequestLocal>>? = runBlocking {
        val mDeferredResult = async {
            requestsUseCases?.getMyRequests()
        }
        mDeferredResult.await()
    }

    fun putRequest(requests: List<Request>) {
        viewModelScope.launch(Dispatchers.IO) {
            requestsUseCases?.putMyRequest(requests)
        }
    }

    fun updateRequest(requests: List<Request>) {
        viewModelScope.launch(Dispatchers.IO) {
            requestsUseCases?.removeAllMyRequests()
            requestsUseCases?.putMyRequest(requests)
        }
    }
}