package com.uzlov.valitova.justcargo.repo.net

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.model.entities.Delivery

interface IDeliveryRepository {
    fun getDelivery() : LiveData<List<Delivery>>
    fun getDelivery(id: String) : LiveData<Delivery?>
    fun putDelivery(delivery: Delivery)
    fun removeDelivery(id: String)
}