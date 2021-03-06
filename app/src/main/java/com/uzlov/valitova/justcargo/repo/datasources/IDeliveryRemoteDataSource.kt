package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.service.BookingRequestStateService
import com.uzlov.valitova.justcargo.service.LookRequestStateService

interface IDeliveryRemoteDataSource {
    fun getDelivery(): LiveData<List<Delivery>>
    fun getDelivery(id: Int): LiveData<Delivery?>
    fun putDelivery(delivery: Delivery)
    fun removeDelivery(id: Long)
    fun getDelivery(id: Long, phone: String): LiveData<Delivery?>
    fun getDeliveriesWithCarrierPhone(phone: String): LiveData<List<Delivery>>
    fun observeDeliveries(
        phone: String,
        listener: BookingRequestStateService.BookingStateListener,
    )

    fun getDeliveriesWithRequestID(id: Long): LiveData<List<Delivery>>
    fun observeSelfRequests(
        phone: String,
        bookingCallback: LookRequestStateService.RequestStateListener,
    )
}