package com.uzlov.valitova.justcargo.repo.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uzlov.valitova.justcargo.data.local.RequestLocal

@Database(entities = [RequestLocal::class], version = 1)
abstract class RoomFavoritesRequestDB : RoomDatabase() {
    abstract val favoriteRequestDao: FavoriteRequestDao
}