package com.uzlov.valitova.justcargo.repo.usecases

import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.repo.net.IDeliveryRepository
import com.uzlov.valitova.justcargo.service.BookingRequestStateService
import javax.inject.Inject

class DeliveryUseCases @Inject constructor(var deliveryRepository: IDeliveryRepository) {
    fun getDelivery() = deliveryRepository.getDelivery()
    fun getDelivery(id: Int) = deliveryRepository.getDelivery(id)
    fun removeDelivery(id: Long) = deliveryRepository.removeDelivery(id)
    fun putDelivery(delivery: Delivery) = deliveryRepository.putDelivery(delivery)
    fun getDelivery(requestId: Long, phoneCarrier: String) =
        deliveryRepository.getDeliveryWithParam(requestId, phoneCarrier)

    fun getDeliveriesWithCarrierPhone(phone: String) =
        deliveryRepository.getDeliveriesWithCarrierPhone(phone)

    fun observeDeliveries(
        phone: String,
        listener: BookingRequestStateService.BookingStateListener,
    ) = deliveryRepository.observeDeliveries(phone, listener)
}