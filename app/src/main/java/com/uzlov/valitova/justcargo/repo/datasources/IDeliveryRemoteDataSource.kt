package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.model.entities.Delivery

interface IDeliveryRemoteDataSource {
    fun getDelivery() : LiveData<List<Delivery>>
    fun getDelivery(id: String) : LiveData<Delivery?>
    fun putDelivery(id: Delivery)
    fun removeDelivery(id: String)
}