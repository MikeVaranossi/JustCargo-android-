package com.uzlov.valitova.justcargo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.viemodels.DeliveryViewModel
import com.uzlov.valitova.justcargo.viemodels.ViewModelFactory
import javax.inject.Inject

/*
* Артем Узлов
* сервис следит за изменением состояния бронирования, отправленного на перевозку груза (грузов)
* */
class BookingRequestStateService : Service(), LifecycleOwner {

    @Inject
    lateinit var authService: AuthService

    @Inject
    lateinit var factoryModel: ViewModelFactory
    lateinit var deliveryModel: DeliveryViewModel

    interface BookingStateListener {
        fun accept(item: Delivery)
        fun reject(item: Delivery)
    }

    private lateinit var lifecycleRegistry: LifecycleRegistry

    private val bookingCallback: BookingStateListener =
        object : BookingStateListener {
            override fun accept(item: Delivery) {
                notify(item,
                    "${item.request?.shortInfo}",
                    getString(R.string.delivery_status_updated))
            }

            override fun reject(item: Delivery) {
                notify(item,
                    "${item.request?.shortInfo}",
                    getString(R.string.your_request_delivery_reject))
            }
        }

    override fun onCreate() {
        lifecycleRegistry = LifecycleRegistry(this)

        applicationContext.appComponent.inject(this)
        super.onCreate()
        deliveryModel = factoryModel.create(DeliveryViewModel::class.java)

        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun onBind(p0: Intent?): IBinder? {
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        authService.currentUser()?.phone?.let { phone ->
            deliveryModel.observeDeliveriesWithPhoneCarrier(phone, bookingCallback)
        }
        return START_STICKY
    }

    private fun notify(delivery: Delivery, title: String, content: String) {
        val notificationHelper =
            NotificationHelper(
                applicationContext,
                title, content)
        notificationHelper.createNotificationChannel()
        notificationHelper.sendNotification()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}