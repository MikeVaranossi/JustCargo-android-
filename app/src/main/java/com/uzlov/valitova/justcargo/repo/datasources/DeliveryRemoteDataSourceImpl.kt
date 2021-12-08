package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.model.entities.Delivery

class DeliveryRemoteDataSourceImpl : IDeliveryRemoteDataSource {

    private val mDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    private val deliveryReference = mDatabase.getReference(Constant.DELIVERY)

    private val deliveryAll = MutableLiveData<List<Delivery>>()
    private val resultDelivery = MutableLiveData<Delivery>()

    override fun getDelivery(): LiveData<List<Delivery>> {
        deliveryReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // send data
                val lis = snapshot.children.map {
                    it.getValue<Delivery>()!!
                }
                deliveryAll.value = lis
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })

        return deliveryAll
    }

    override fun getDelivery(id: String): LiveData<Delivery?> {
        deliveryReference.child(id).get().addOnSuccessListener {
            resultDelivery.value = it.getValue<Delivery>()
        }
        return resultDelivery
    }

    override fun putDelivery(delivery: Delivery) {
        deliveryReference.child(delivery.id.toString()).setValue(delivery)
    }

    override fun removeDelivery(id: String) {
        deliveryReference.child(id).removeValue()
    }
}