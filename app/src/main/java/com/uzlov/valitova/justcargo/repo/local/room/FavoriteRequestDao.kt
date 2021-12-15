package com.uzlov.valitova.justcargo.repo.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal

@Dao
interface FavoriteRequestDao {
    @Query("SELECT * FROM FavoriteRequestLocal")
    fun getFavoriteRequests():  LiveData<List<FavoriteRequestLocal>>

    @Query("SELECT * FROM FavoriteRequestLocal WHERE id LIKE :id")
    fun getFavoriteRequest(id: Long): LiveData<FavoriteRequestLocal?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRequest(favoriteRequests: FavoriteRequestLocal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRequest(favoriteRequests: List<FavoriteRequestLocal>)

    @Insert
    suspend fun insertRequest(vararg favoriteRequests: FavoriteRequestLocal)

    @Delete
    suspend fun removeRequest(favoriteRequests: FavoriteRequestLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRequest(favoriteRequests: FavoriteRequestLocal)
}
