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
* сервис следит за присылаемыми бронированиями, отправленного на перевозку груза (грузов)
* // висит на стороне отправителя
* */
class LookRequestStateService : Service(), LifecycleOwner {

    @Inject
    lateinit var authService: AuthService

    @Inject
    lateinit var factoryModel: ViewModelFactory
    private lateinit var deliveryModel: DeliveryViewModel

    interface RequestStateListener {
        fun booking(item: Delivery) // перевозчик прислал запрос бронирования
        fun rejectBooking(item: Delivery) // перевозчик отменил свое бронирование
    }

    private lateinit var lifecycleRegistry: LifecycleRegistry

    private val bookingCallback: RequestStateListener =
        object : RequestStateListener {
            override fun booking(item: Delivery) {
                notify(item,
                    getString(R.string.new_request),
                    "${item.trip?.carrier?.name ?: "Кто то"} ГОТОВ увезти Ваш груз")
            }

            override fun rejectBooking(item: Delivery) {
                notify(item,
                    getString(R.string.booking_is_rejected),
                    "${item.trip?.carrier?.name ?: "Кто то"} НЕ ГОТОВ увезти Ваш груз")
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
            deliveryModel.observeSelfRequests(phone, bookingCallback)
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