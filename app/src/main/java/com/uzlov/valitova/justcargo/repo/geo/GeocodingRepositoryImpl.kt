package com.uzlov.valitova.justcargo.repo.geo

import com.uzlov.valitova.justcargo.data.geocoding.GeoPoint
import com.uzlov.valitova.justcargo.repo.api.GeoApi
import retrofit2.Response
import javax.inject.Inject

class GeocodingRepositoryImpl @Inject constructor(private var api: GeoApi) : IGeocodingRepository {

    override suspend fun getAddress(latitude: String, longitude: String): Response<GeoPoint?>? {
        return api.getGeocodingAddress(latitude = latitude, longitude = longitude)
    }
}