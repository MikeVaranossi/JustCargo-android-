package com.uzlov.valitova.justcargo.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootLoaderService : BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        context?.startService(Intent(context, BookingRequestStateService::class.java))
    }
}