package com.uzlov.valitova.justcargo.repo.repositories

import com.uzlov.valitova.justcargo.model.entities.Delivery
import com.uzlov.valitova.justcargo.repo.datasources.DeliveryRemoteDataSource
import com.uzlov.valitova.justcargo.repo.net.IDeliveryRepository
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(var remoteDataSource: DeliveryRemoteDataSource) :
    IDeliveryRepository {
    override fun getDelivery(): List<Delivery> = remoteDataSource.getDelivery()

    override fun getDelivery(id: Long): Delivery? = remoteDataSource.getDelivery(id)

    override fun putDelivery(id: Delivery) = remoteDataSource.putDelivery(id)

    override fun removeDelivery(id: Long) = remoteDataSource.removeDelivery(id)
}