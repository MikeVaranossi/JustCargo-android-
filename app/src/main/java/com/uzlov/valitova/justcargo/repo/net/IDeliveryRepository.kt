package com.uzlov.valitova.justcargo.repo.net

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Delivery

interface IDeliveryRepository {
    fun getDelivery() : LiveData<List<Delivery>>
    fun getDelivery(id: Int) : LiveData<Delivery?>
    fun putDelivery(delivery: Delivery)
    fun removeDelivery(id: Int)
}