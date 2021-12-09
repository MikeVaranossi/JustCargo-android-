package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.data.net.User

class UserRemoteDataSourceImpl : IUsersRemoteDataSource {

    private val mDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    private val usersReference = mDatabase.getReference(Constant.USERS)

    private val resultAll = MutableLiveData<List<User>>()
    private val resultUser = MutableLiveData<User>()


    override fun getUsers(): LiveData<List<User>> {
        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // send data
                val lis = snapshot.children.map {
                    it.getValue<User>()!!
                }
                resultAll.value = lis
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })

        return resultAll
    }

    override fun getUser(id: Int): LiveData<User?> {
        usersReference.child(id.toString()).get().addOnSuccessListener {
            resultUser.value = it.getValue<User>()
        }
        return resultUser
    }

    override fun removeUsers(id: Int) {
        usersReference.child(id.toString()).removeValue()
    }

    override fun putUser(user: User) {
        usersReference.child(user.phone.toString()).setValue(user)
    }
}