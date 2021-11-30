package com.uzlov.valitova.justcargo.repo.datasources

import android.os.Build
import androidx.annotation.RequiresApi
import com.uzlov.valitova.justcargo.model.entities.Delivery
import java.time.OffsetTime

class DeliveryRemoteDataSourceImpl : DeliveryRemoteDataSource {
    override fun getDelivery(): List<Delivery> {
        return emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDelivery(id: Long): Delivery? {
        return Delivery(
            0,
            null,
            null,
            OffsetTime.now(),
            OffsetTime.now()
        )
    }

    override fun putDelivery(id: Delivery) {
    }

    override fun removeDelivery(id: Long) {

    }
}