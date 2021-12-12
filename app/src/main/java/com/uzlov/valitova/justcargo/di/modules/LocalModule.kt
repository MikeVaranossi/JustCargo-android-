package com.uzlov.valitova.justcargo.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.uzlov.valitova.justcargo.repo.local.room.FavoriteRequestDao
import com.uzlov.valitova.justcargo.repo.local.room.RoomFavoritesRequestDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Singleton
    @Provides
    fun getLocalName() : String = "com.just_cargo_preferences"

    @Singleton
    @Provides
    fun sharedPreference(app: Context) : SharedPreferences = app.getSharedPreferences(getLocalName(), Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun database(app: Context) : RoomFavoritesRequestDB = Room.databaseBuilder(app, RoomFavoritesRequestDB::class.java, getLocalName())
        .fallbackToDestructiveMigration() // TODO: need migrations!
        .build()

    fun dao(db: RoomFavoritesRequestDB) : FavoriteRequestDao = db.favoriteRequestDao
}