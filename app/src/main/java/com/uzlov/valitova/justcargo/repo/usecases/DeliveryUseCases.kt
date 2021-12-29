package com.uzlov.valitova.justcargo.repo.usecases

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository
import com.uzlov.valitova.justcargo.repo.net.IDeliveryRepository
import com.uzlov.valitova.justcargo.service.BookingRequestStateService
import com.uzlov.valitova.justcargo.service.LookRequestStateService
import javax.inject.Inject

class DeliveryUseCases @Inject constructor(
    var deliveryRepository: IDeliveryRepository,
    var localRepository: ILocalRepository,
) {
    fun getDelivery() = deliveryRepository.getDelivery()
    fun getDelivery(id: Int) = deliveryRepository.getDelivery(id)
    suspend fun removeDelivery(delivery: Delivery) {
        delivery.request?.id?.let { localRepository.removeMyRequest(it) }
        delivery.id?.let { deliveryRepository.removeDelivery(it) }

    }

    fun putDelivery(delivery: Delivery) = deliveryRepository.putDelivery(delivery)
    fun getDelivery(requestId: Long, phoneCarrier: String): LiveData<Delivery?> =
        deliveryRepository.getDeliveryWithParam(requestId, phoneCarrier)

    fun getDeliveriesWithCarrierPhone(phone: String): LiveData<List<Delivery>> {
        return deliveryRepository.getDeliveriesWithCarrierPhone(phone)
    }


    fun observeDeliveries(
        phone: String,
        listener: BookingRequestStateService.BookingStateListener,
    ) = deliveryRepository.observeDeliveries(phone, listener)

    fun getDeliveriesWithRequestID(id: Long): LiveData<List<Delivery>> =
        deliveryRepository.getDeliveriesWithRequestID(id)

    fun observeSelfRequests(
        phone: String,
        bookingCallback: LookRequestStateService.RequestStateListener,
    ) = deliveryRepository.observeSelfRequests(phone, bookingCallback)
}