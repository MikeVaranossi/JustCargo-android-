package com.uzlov.valitova.justcargo.repo.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.model.entities.Delivery
import com.uzlov.valitova.justcargo.model.entities.Request
import com.uzlov.valitova.justcargo.repo.datasources.DeliveryRemoteDataSource
import com.uzlov.valitova.justcargo.repo.net.IDeliveryRepository
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(var remoteDataSource: DeliveryRemoteDataSource) :
    IDeliveryRepository {

    override fun getDelivery(): LiveData<List<Delivery>> = remoteDataSource.getDelivery()

    override fun getDelivery(id: String):  LiveData<Delivery?> = remoteDataSource.getDelivery(id)

    override fun putDelivery(delivery: Delivery) = remoteDataSource.putDelivery(delivery)

    override fun removeDelivery(id: String) = remoteDataSource.removeDelivery(id)
}