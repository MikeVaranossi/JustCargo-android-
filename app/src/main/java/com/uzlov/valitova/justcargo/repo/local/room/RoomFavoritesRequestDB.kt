package com.uzlov.valitova.justcargo.repo.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.local.MyRequestLocal

@Database(entities = [FavoriteRequestLocal::class, MyRequestLocal::class], version = 4)
abstract class RoomFavoritesRequestDB : RoomDatabase() {
    abstract val favoriteRequestDao: FavoriteRequestDao
    abstract val myRequestDao: MyRequestDao
}