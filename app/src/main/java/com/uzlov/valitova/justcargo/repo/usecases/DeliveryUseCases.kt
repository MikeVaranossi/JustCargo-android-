package com.uzlov.valitova.justcargo.repo.usecases

import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.repo.net.IDeliveryRepository
import javax.inject.Inject

class DeliveryUseCases @Inject constructor(var deliveryRepository: IDeliveryRepository) {
    fun getDelivery() = deliveryRepository.getDelivery()
    fun getDelivery(id: String) = deliveryRepository.getDelivery(id)
    fun removeDelivery(id: String) = deliveryRepository.removeDelivery(id)
    fun putDelivery(delivery: Delivery) = deliveryRepository.putDelivery(delivery)
}