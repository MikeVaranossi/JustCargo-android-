package com.uzlov.valitova.justcargo.repo.geo

import com.uzlov.valitova.justcargo.data.geocoding.GeoPoint
import retrofit2.Response

interface IGeocodingRepository {
    suspend fun getAddress(latitude: String, longitude: String): Response<GeoPoint?>?
}