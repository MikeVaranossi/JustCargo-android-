package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.model.entities.Request

class RequestsRemoteDataSourceImpl : RequestsRemoteDataSource {
    private val mDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    private val reqReference = mDatabase.getReference(Constant.REQUESTS)

    private val resultAll = MutableLiveData<List<Request>>()
    private val resultRequest = MutableLiveData<Request>()


    override fun getRequests(): LiveData<List<Request>> {
        reqReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // send data
                val lis = snapshot.children.map {
                    it.getValue<Request>()!!
                }
                resultAll.value = lis
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })

        return resultAll
    }

    override fun getRequest(id: String): LiveData<Request?> {
        reqReference.child(id).get().addOnSuccessListener {
            resultRequest.value = it.getValue<Request>()
        }
        return resultRequest
    }

    override fun removeRequest(id: String) {
        reqReference.child(id).removeValue()
    }

    override fun putRequest(request: Request) {
        reqReference.child(request.id.toString()).setValue(request)
    }
}