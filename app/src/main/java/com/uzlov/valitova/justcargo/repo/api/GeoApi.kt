package com.uzlov.valitova.justcargo.repo.api

import com.uzlov.valitova.justcargo.data.geocoding.GeoPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApi {
    @GET("reverse")
    suspend fun getGeocodingAddress(
        @Query("format") format: String = "json",
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("addressdetails") addressdetails: String = "1"
    ): Response<GeoPoint?>?
}