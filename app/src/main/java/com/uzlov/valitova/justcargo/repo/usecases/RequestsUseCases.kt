package com.uzlov.valitova.justcargo.repo.usecases

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.app.toFavoriteRequestLocal
import com.uzlov.valitova.justcargo.app.toMyRequestLocal
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.local.MyRequestLocal
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import javax.inject.Inject

class RequestsUseCases @Inject constructor(
    private var requestRepository: IRequestsRepository,
    private var localRequestRepository: ILocalRepository,
) {

    // for remote requests
    fun getRequests(id: Long) = requestRepository.getRequest(id)
    fun getRequests() = requestRepository.getRequests()
    fun removeRequests(id: Long) = requestRepository.removeRequest(id)
    fun putRequest(request: Request) = requestRepository.putRequest(request)
    fun getRequestsWithStatus(id: Int) = requestRepository.getRequestsWithStatus(id)
    fun getRequestsWithPhone(phone: String) = requestRepository.getRequestsWithPhone(phone)
    fun getRequestsWithUserID(id: Int) = requestRepository.getRequestsWithUserID(id)

    fun searchRequest(
        from: String,
        to: String,
        dateTimeStart: Long,
    ): LiveData<List<Request>> = requestRepository.searchRequest(
        from,
        to,
        dateTimeStart
    )

    fun searchRequest(
        from: String,
        to: String,
    ): LiveData<List<Request>> = requestRepository.searchRequest(
        from,
        to
    )


    //    for local favourite requests
    suspend fun getFavouritesRequests(): LiveData<List<FavoriteRequestLocal>> =
        localRequestRepository.getFavRequests()
    suspend fun getFavouriteRequest(id: Long): LiveData<FavoriteRequestLocal?> =
        localRequestRepository.getFavRequest(id)
    suspend fun removeFavouriteRequest(request: FavoriteRequestLocal) =
        localRequestRepository.removeFavRequest(request)
    suspend fun putFavouriteRequest(request: FavoriteRequestLocal) =
        localRequestRepository.putFavRequest(request)
    suspend fun updateFavouriteRequest(request: FavoriteRequestLocal) =
        localRequestRepository.updateFavRequest(request)
    suspend fun getFavouritesIDs(): List<Long> = localRequestRepository.getFavIDsRequests()


    //     for local my requests
    suspend fun getMyRequests(): LiveData<List<MyRequestLocal>> =
        localRequestRepository.getMyRequests()

    suspend fun putMyRequest(requests: List<Request>) {
         val list = requests.map { request -> request.toMyRequestLocal() }
         localRequestRepository.putMyRequest(list)
     }

    suspend fun removeAllMyRequests() {
        localRequestRepository.removeAllMyRequests()
    }
}
