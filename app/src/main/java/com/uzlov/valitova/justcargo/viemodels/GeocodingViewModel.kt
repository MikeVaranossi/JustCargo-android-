package com.uzlov.valitova.justcargo.viemodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uzlov.valitova.justcargo.data.geocoding.GeoPoint
import com.uzlov.valitova.justcargo.repo.geo.IGeocodingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeocodingViewModel @Inject constructor(private var geoRepository: IGeocodingRepository) :
    BaseViewModel() {

    private val result = MutableLiveData<GeoPoint>()

    fun fetchGeocoding(lat: String, lng: String): LiveData<GeoPoint> {
        //отменяем предыдущие запросы
        viewModelScope.coroutineContext.cancelChildren()

        // запрашиваем новый адрес
        viewModelScope.launch {
            val response = geoRepository.getAddress(latitude = lat, longitude = lng)
            withContext(Dispatchers.Main) {
                if (response != null && response.isSuccessful) {
                    response.body()?.let {
                        result.value = it
                    }
                } else {
                    Log.e(javaClass.simpleName, "fetchGeocoding: ERROR")
                }
            }
        }
        return result
    }
}