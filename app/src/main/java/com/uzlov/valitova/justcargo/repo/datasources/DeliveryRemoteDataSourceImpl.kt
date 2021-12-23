package com.uzlov.valitova.justcargo.repo.datasources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.data.net.Delivery

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

    override fun getDelivery(id: Int): LiveData<Delivery?> {
        deliveryReference.child(id.toString()).get().addOnSuccessListener {
            resultDelivery.value = it.getValue<Delivery>()
        }
        return resultDelivery
    }

    // получает "доставку" с определенным id заявки и телефоном перевозчиком
    override fun getDelivery(id: Long, phone: String): LiveData<Delivery?> {
        val result = MutableLiveData<Delivery?>()
        deliveryReference.orderByChild("request/id").equalTo(id.toDouble())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null){
                        result.value = snapshot.children.map {
                            it.getValue<Delivery>()
                        }.first {
                            it?.trip?.carrier?.phone == phone
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException().printStackTrace()
                }
            })

        return result
    }

    override fun putDelivery(delivery: Delivery) {
        deliveryReference.child(delivery.id.toString()).setValue(delivery)
    }

    override fun removeDelivery(id: Int) {
        deliveryReference.child(id.toString()).removeValue()
    }

    override fun getDeliveriesWithCarrierPhone(phone: String): LiveData<List<Delivery>> {
        val result = MutableLiveData<List<Delivery>>()
        deliveryReference.orderByChild("trip/carrier/phone").equalTo(phone)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null){
                        result.value = snapshot.children.map {
                            it.getValue<Delivery>()!!
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException().printStackTrace()
                }
            })

        return result
    }
}