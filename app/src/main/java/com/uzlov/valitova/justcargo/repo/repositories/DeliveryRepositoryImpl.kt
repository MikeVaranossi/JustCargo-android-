package com.uzlov.valitova.justcargo.repo.repositories

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.repo.datasources.IDeliveryRemoteDataSource
import com.uzlov.valitova.justcargo.repo.net.IDeliveryRepository
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(var remoteDataSource: IDeliveryRemoteDataSource) :
    IDeliveryRepository {

    override fun getDelivery(): LiveData<List<Delivery>> = remoteDataSource.getDelivery()

    override fun getDelivery(id: Int):  LiveData<Delivery?> = remoteDataSource.getDelivery(id)

    override fun putDelivery(delivery: Delivery) = remoteDataSource.putDelivery(delivery)

    override fun removeDelivery(id: Int) = remoteDataSource.removeDelivery(id)
}