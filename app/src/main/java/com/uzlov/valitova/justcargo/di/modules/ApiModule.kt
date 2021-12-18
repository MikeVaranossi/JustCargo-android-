package com.uzlov.valitova.justcargo.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uzlov.valitova.justcargo.repo.api.ServerApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    fun url(): String = "____"

    @Provides
    fun port(): String = "34578"

    @Named("baseUrl")
    @Provides
    fun baseUrl(): String = "http://${url()}:${port()}/"

    @Singleton
    @Provides
    fun api(@Named("baseUrl") baseUrl: String, gson: Gson): ServerApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client())
            .build()
            .create(ServerApi::class.java)
    }

    @Singleton
    @Provides
    fun client(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder()
        .create()

}