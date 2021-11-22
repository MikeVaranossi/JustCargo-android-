package com.uzlov.valitova.justcargo.app

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.SuperscriptSpan


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