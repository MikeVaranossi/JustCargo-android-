package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Delivery

interface IDeliveryRemoteDataSource {
    fun getDelivery() : LiveData<List<Delivery>>
    fun getDelivery(id: Int) : LiveData<Delivery?>
    fun putDelivery(delivery: Delivery)
    fun removeDelivery(id: Int)
}