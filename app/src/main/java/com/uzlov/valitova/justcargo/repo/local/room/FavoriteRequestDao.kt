package com.uzlov.valitova.justcargo.repo.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.uzlov.valitova.justcargo.data.local.RequestLocal

@Dao
interface FavoriteRequestDao {
    @Query("SELECT * FROM favoriteRequest")
    suspend fun getAll(): List<RequestLocal>

    @Insert
    suspend fun insertAll(users: List<RequestLocal>)

    @Delete
    suspend fun delete(requestLocal: RequestLocal)
}
