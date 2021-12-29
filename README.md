<p align="center">
  <img src="https://github.com/MikeVaranossi/JustCargo-android-/blob/master/app/src/main/res/drawable-v24/splash.png" />
</p>

## ГРУЗ по Пути

 Description

 The CARGO BY THE ROUTE project, a service and mobile application that helps freight carriers and shippers to interact faster with each other on the same site.

The platform combines:
carriers ranging from large transport companies to private cars
senders ranging from large businesses to private individuals

## Introduction

This application contains the following screens:

### Sender

* Registration
* Formation of applications, including using the card
* Caching requests
* Personal Area

### Carrier

* Registration
* Search for cargo, including using an interactive map
* Acceptance / booking of applications
* Caching of accepted orders
* Ability to add a request to favorites
* Personal Area

## Presentation layer

This app is a Single-Activity app
The app uses a Model-View-ViewModel (MVVM) architecture for the presentation layer. Each of the fragments corresponds to a MVVM View.
The View and ViewModel communicate using LiveData and general good principles.

## Third Party Libraries Used

* Room
* Dagger2
* Coroutines
* GoogleMapsApi
* FireBase

