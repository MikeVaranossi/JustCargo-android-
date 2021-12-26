package com.uzlov.valitova.justcargo.repo.net

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.service.BookingRequestStateService

interface IDeliveryRepository {
    fun getDelivery(): LiveData<List<Delivery>>
    fun getDelivery(id: Int): LiveData<Delivery?>
    fun getDeliveryWithParam(id: Long, phone: String): LiveData<Delivery?>
    fun putDelivery(delivery: Delivery)
    fun removeDelivery(id: Long)
    fun getDeliveriesWithCarrierPhone(phone: String): LiveData<List<Delivery>>
    fun observeDeliveries(
        phone: String,
        listener: BookingRequestStateService.BookingStateListener,
    )

    fun getDeliveriesWithRequestID(id: Long): LiveData<List<Delivery>>
}