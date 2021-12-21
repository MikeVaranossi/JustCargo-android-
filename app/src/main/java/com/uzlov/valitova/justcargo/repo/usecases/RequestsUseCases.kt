package com.uzlov.valitova.justcargo.repo.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import javax.inject.Inject

class RequestsUseCases @Inject constructor(
   private var requestRepository: IRequestsRepository,
    private var localRequestRepository: ILocalRepository,
) {

    // for remote requests
    fun getRequests(id: Int) = requestRepository.getRequest(id)
    fun getRequests() = requestRepository.getRequests()
    fun removeRequests(id: Int) = requestRepository.removeRequest(id)
    fun putRequest(request: Request) = requestRepository.putRequest(request)
    fun getRequestsWithStatus(id: Int) = requestRepository.getRequestsWithStatus(id)
    fun getRequestsWithPhone(phone: String) = requestRepository.getRequestsWithPhone(phone)
    fun getRequestsWithUserID(id: Int) = requestRepository.getRequestsWithUserID(id)

//    for local requests
    suspend fun getFavouritesRequests() : LiveData<List<FavoriteRequestLocal>> = localRequestRepository.getRequests()
    suspend fun getFavouriteRequest(id: Long) : LiveData<FavoriteRequestLocal?> = localRequestRepository.getRequest(id)
    suspend fun removeFavouriteRequest(request: FavoriteRequestLocal) = localRequestRepository.removeRequest(request)
    suspend fun putFavouriteRequest(request: FavoriteRequestLocal) = localRequestRepository.putRequest(request)
    suspend fun updateFavouriteRequest(request: FavoriteRequestLocal) = localRequestRepository.updateRequest(request)

    // возвращает множество ID-ков избранных заявок
    suspend fun getFavouritesIDs()  : List<Long> = localRequestRepository.getIDsRequests()
}
