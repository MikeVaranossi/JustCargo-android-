package com.uzlov.valitova.justcargo.repo.net

import com.uzlov.valitova.justcargo.model.entities.Delivery

interface IDeliveryRepository {
    fun getDelivery() : List<Delivery>
    fun getDelivery(id: Long) : Delivery?
    fun putDelivery(id: Delivery)
    fun removeDelivery(id: Long)
}