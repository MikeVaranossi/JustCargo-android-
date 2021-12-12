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

    @Insert
    fun insertRequest(favoriteRequests: FavoriteRequestLocal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRequest(favoriteRequests: List<FavoriteRequestLocal>)

    @Insert
    fun insertRequest(vararg favoriteRequests: FavoriteRequestLocal)

    @Delete
    fun removeRequest(favoriteRequests: FavoriteRequestLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRequest(favoriteRequests: FavoriteRequestLocal)
}
