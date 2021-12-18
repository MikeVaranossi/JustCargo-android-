package com.uzlov.valitova.justcargo.ui.fragments

interface IViewItemAdapter {
    fun getShortInfoItem(): String?
    fun getDeliveryTimeItem() : Long?
    fun getCostItem() : Int?
    fun getDepartureItem() : String?
    fun getDestinationItem() : String?
}