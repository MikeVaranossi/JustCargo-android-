package com.uzlov.valitova.justcargo.repo.local.room

import androidx.room.RoomDatabase

abstract class RoomDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao
}