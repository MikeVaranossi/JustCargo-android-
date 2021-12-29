package com.uzlov.valitova.justcargo.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.repo.usecases.DeliveryUseCases
import com.uzlov.valitova.justcargo.service.BookingRequestStateService
import com.uzlov.valitova.justcargo.service.LookRequestStateService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeliveryViewModel @Inject constructor(private var deliveryUseCases: DeliveryUseCases) :
    BaseViewModel() {

    fun getDeliveries() = deliveryUseCases.getDelivery()

    fun getDeliveriesWithCarrierPhone(phone: String) =
        deliveryUseCases.getDeliveriesWithCarrierPhone(phone)

    fun getDelivery(id: Int) = deliveryUseCases.getDelivery(id)

    fun getDeliveriesWithRequestID(id: Long) = deliveryUseCases.getDeliveriesWithRequestID(id)

    fun addDelivery(delivery: Delivery) = deliveryUseCases.putDelivery(delivery)

    fun removeDelivery(delivery: Delivery) {
        viewModelScope.coroutineContext.cancelChildren()
        viewModelScope.launch(Dispatchers.IO) {
            deliveryUseCases.removeDelivery(delivery)
        }
    }

    fun getDeliveryWithParam(requestId: Long, phoneCarrier: String): LiveData<Delivery?> =
        deliveryUseCases.getDelivery(requestId, phoneCarrier)

    fun observeDeliveriesWithPhoneCarrier(
        phone: String, listener: BookingRequestStateService.BookingStateListener,
    ) = deliveryUseCases.observeDeliveries(phone, listener)

    fun observeSelfRequests(
        phone: String,
        bookingCallback: LookRequestStateService.RequestStateListener,
    ) = deliveryUseCases.observeSelfRequests(phone, bookingCallback)
}