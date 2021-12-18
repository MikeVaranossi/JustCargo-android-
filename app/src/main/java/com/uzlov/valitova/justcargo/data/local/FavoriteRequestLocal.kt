package com.uzlov.valitova.justcargo.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uzlov.valitova.justcargo.data.net.RequestStatus
import com.uzlov.valitova.justcargo.ui.fragments.IViewItemAdapter
import kotlinx.parcelize.Parcelize
import java.time.OffsetTime

@Entity
@Parcelize
data class FavoriteRequestLocal (
    @PrimaryKey var id: Long? = 0,
    @ColumnInfo(name = "requestTime") var requestTime: Long? = 0,
    @ColumnInfo(name = "deliveryTime") var deliveryTime: Long? = 0,
    @ColumnInfo(name = "cost") var cost: Int? = 0,
    @ColumnInfo(name = "departure") var departure: String? = "",
    @ColumnInfo(name = "destination") var destination: String? = "",
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "shortInfo") var shortInfo: String? = "",
    @ColumnInfo(name = "weight") var weight: Float? = 0F,
    @ColumnInfo(name = "length") var length: Int? = 0,
    @ColumnInfo(name = "width") var width: Int? = 0,
    @ColumnInfo(name = "height") var height: Int? = 0,
    @ColumnInfo(name = "status") var status: String? = null,
) : Parcelable, IViewItemAdapter {
    override fun getShortInfoItem() = shortInfo

    override fun getDeliveryTimeItem() = deliveryTime

    override fun getCostItem() = cost

    override fun getDepartureItem() = departure

    override fun getDestinationItem() = destination
}