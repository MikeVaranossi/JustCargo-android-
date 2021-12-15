package com.uzlov.valitova.justcargo.app

import android.app.DownloadManager
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.SuperscriptSpan
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Request


fun String.lastToPower(): SpannableStringBuilder =
    if (length < 1) {
        SpannableStringBuilder()
    } else {
        SpannableStringBuilder(this).also {
            it.setSpan(SuperscriptSpan(),
                it.length - 1, it.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            it.setSpan(AbsoluteSizeSpan(26),
                it.length - 1, it.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

fun Request.toFavoriteRequestLocal(): FavoriteRequestLocal {
    return FavoriteRequestLocal(
        id = this.id,
        requestTime = this.requestTime,
        cost = this.cost,
        departure = this.departure,
        destination = this.destination,
        description = this.description,
        shortInfo = this.shortInfo,
        weight = this.weight,
        length = this.length,
        width = this.width,
        height = this.height,
        status = this.status?.name
    )
}