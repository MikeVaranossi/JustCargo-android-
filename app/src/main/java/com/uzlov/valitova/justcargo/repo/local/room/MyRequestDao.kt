package com.uzlov.valitova.justcargo.repo.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uzlov.valitova.justcargo.data.local.MyRequestLocal

@Dao
interface MyRequestDao {
    @Query("SELECT * FROM MyRequestLocal")
    fun getMyRequests():  LiveData<List<MyRequestLocal>>

    @Query("SELECT * FROM MyRequestLocal WHERE id = :id")
    fun getMyRequest(id: Long): LiveData<MyRequestLocal?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyRequest(myRequest: MyRequestLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyRequest(myRequest: List<MyRequestLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyRequest(vararg myRequest: MyRequestLocal)

    @Delete
    suspend fun removeMyRequest(myRequest: MyRequestLocal)

    @Query("DELETE FROM MyRequestLocal WHERE id = :id")
    suspend fun removeMyRequest(id: Long)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMyRequest(myRequest: MyRequestLocal)
}