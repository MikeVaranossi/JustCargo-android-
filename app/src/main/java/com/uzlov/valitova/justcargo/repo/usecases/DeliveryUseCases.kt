package com.uzlov.valitova.justcargo.repo.usecases

import com.uzlov.valitova.justcargo.model.entities.Delivery
import com.uzlov.valitova.justcargo.repo.net.IDeliveryRepository
import javax.inject.Inject

class DeliveryUseCases @Inject constructor(var deliveryRepository: IDeliveryRepository) {
    suspend fun getDelivery() = deliveryRepository.getDelivery()
    suspend fun getDelivery(id: String) = deliveryRepository.getDelivery(id)
    suspend fun putDelivery(id: String) = deliveryRepository.removeDelivery(id)
    suspend fun putDelivery(delivery: Delivery) = deliveryRepository.putDelivery(delivery)
}