package com.uzlov.valitova.justcargo.viemodels

import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.repo.usecases.DeliveryUseCases
import javax.inject.Inject

class DeliveryViewModel @Inject constructor(private var deliveryUseCases: DeliveryUseCases?)  : BaseViewModel() {

    fun getDeliveries() = deliveryUseCases?.getDelivery()
    fun getDeliveriesWithCarrierPhone(phone: String) = deliveryUseCases?.getDeliveriesWithCarrierPhone(phone)

    fun getDelivery(id: Int) = deliveryUseCases?.getDelivery(id)

    fun addDelivery(delivery: Delivery) = deliveryUseCases?.putDelivery(delivery)

    fun removeDelivery(id: Int) = deliveryUseCases?.removeDelivery(id)

    fun getDeliveryWithParam(requestId: Long, phoneCarrier: String) = deliveryUseCases?.getDelivery(requestId, phoneCarrier)
}