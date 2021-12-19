package com.uzlov.valitova.justcargo.data.geocoding

data class GeoPoint(
    val address: Address?,
    val boundingbox: List<String>?,
    val display_name: String?,
    val lat: String?,
    val licence: String?,
    val lon: String?,
    val osm_id: String?,
    val osm_type: String?,
    val place_id: String?
) {
    val prettyAddress get() = "${address?.country ?: ""}, ${address?.city ?: address?.state ?: ""}, ${address?.road ?: ""}, ${address?.house_number ?: ""}"
    val addressStr get() = address
}