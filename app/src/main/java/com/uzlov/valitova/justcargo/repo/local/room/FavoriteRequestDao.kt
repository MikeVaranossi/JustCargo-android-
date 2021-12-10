package com.uzlov.valitova.justcargo.repo.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal

@Dao
interface FavoriteRequestDao {
    @Query("SELECT * FROM FavoriteRequestLocal")
    fun getFavoriteRequests(): List<FavoriteRequestLocal>

    @Query("SELECT * FROM FavoriteRequestLocal WHERE id LIKE :id")
    fun getFavoriteRequest(id: Long): FavoriteRequestLocal

    @Insert
    fun insertRequest(favoriteRequests: FavoriteRequestLocal)

    @Delete
    fun removeRequest(favoriteRequests: FavoriteRequestLocal)
}
