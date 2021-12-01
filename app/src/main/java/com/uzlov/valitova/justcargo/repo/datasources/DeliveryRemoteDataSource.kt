package com.uzlov.valitova.justcargo.repo.datasources

import com.uzlov.valitova.justcargo.model.entities.Delivery

interface DeliveryRemoteDataSource {
    fun getDelivery() : List<Delivery>
    fun getDelivery(id: Long) : Delivery?
    fun putDelivery(id: Delivery)
    fun removeDelivery(id: Long)
}