package com.uzlov.valitova.justcargo.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.uzlov.valitova.justcargo.repo.datasources.IRequestsLocalDataSource
import com.uzlov.valitova.justcargo.repo.datasources.RequestsLocalDataSourceImpl
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository
import com.uzlov.valitova.justcargo.repo.local.room.FavoriteRequestDao
import com.uzlov.valitova.justcargo.repo.local.room.MyRequestDao
import com.uzlov.valitova.justcargo.repo.local.room.RoomFavoritesRequestDB
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import com.uzlov.valitova.justcargo.repo.repositories.LocalRequestRepositoryImpl
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
    fun database(app: Context): RoomFavoritesRequestDB = Room.databaseBuilder(app, RoomFavoritesRequestDB::class.java, getLocalName())
        .fallbackToDestructiveMigration() // TODO: need migrations!
        .build()

    @Singleton
    @Provides
    fun provideFavouriteDAO(db: RoomFavoritesRequestDB): FavoriteRequestDao = db.favoriteRequestDao

    @Singleton
    @Provides
    fun provideMyDAO(db: RoomFavoritesRequestDB): MyRequestDao = db.myRequestDao

    @Singleton
    @Provides
    fun provideRequestLocalDataSources(daoFavourite : FavoriteRequestDao, daoMy: MyRequestDao): IRequestsLocalDataSource = RequestsLocalDataSourceImpl(daoFavourite, daoMy)

    @Singleton
    @Provides
    fun provideRequestLocalRepository(dataSource: IRequestsLocalDataSource): ILocalRepository = LocalRequestRepositoryImpl(dataSource)
}