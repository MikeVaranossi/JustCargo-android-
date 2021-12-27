package com.uzlov.valitova.justcargo.ui.fragments.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentMapDeliveriesBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.SearchFragment
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailCarrierFragment
import com.uzlov.valitova.justcargo.viemodels.RequestsViewModel
import javax.inject.Inject


class MapDeliveriesFragment private constructor() :
    BaseFragment<FragmentMapDeliveriesBinding>(
        FragmentMapDeliveriesBinding::inflate
    ) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var model: RequestsViewModel
    private var requests: List<Request> = arrayListOf()
    private var searchRequest: Request = Request()

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        model = viewModelFactory.create(RequestsViewModel::class.java)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.label_home_carrier_fragment)
            it.setDisplayHomeAsUpEnabled(false)
        }
        arguments?.let {
            it.getParcelable<Request>(Constant.KEY_REQUESTS_OBJECT)?.let { _request ->
                searchRequest = _request.copy()
            }
        }
        if (searchRequest.departure.isNullOrEmpty() && searchRequest.destination.isNullOrEmpty()) {
            getLatLng()
        } else {
            getSearchLatLng(
                searchRequest.departure!!,
                searchRequest.destination!!,
                searchRequest.deliveryTime!!
            )
        }
        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_fragment_carrier
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            googleMap.clear()
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                requests.forEach {
                    it.let {
                        bounds.include(
                            LatLng(
                                it.departureLatitude!!,
                                it.departureLongitude!!
                            )
                        )
                    }
                }
                if (requests.isNotEmpty()) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(),50,50, 15))
                    addMarkers(googleMap)
                }

            }
            googleMap.setOnMarkerClickListener { marker ->
                openFragment(RequestDetailCarrierFragment.newInstance(requests[marker.zIndex.toInt()]))
                return@setOnMarkerClickListener false
            }
        }

        viewBinding.buttonFindCargo.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment.newInstance())
                .commit()
        }
    }

    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getLatLng() {
        model.getRequests()?.observe(this, {
            requests = it

        })
    }

    private fun getSearchLatLng(from: String, to: String, time: Long) {

        model.searchRequest(from, to, time)?.observe(this, {
            requests = it

        })
    }

    private fun addMarkers(googleMap: GoogleMap) {

        requests.forEach {
            it.let {
                googleMap.addMarker(
                    MarkerOptions()
                        .title(it.shortInfo)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_orange))
                        .position(LatLng(it.departureLatitude!!, it.departureLongitude!!))
                        .zIndex(requests.indexOf(it).toFloat())
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bell_action -> Toast.makeText(
                context,
                "Здесь будут доступные уведомления",
                Toast.LENGTH_SHORT
            ).show()
            R.id.share_action -> Toast.makeText(
                context,
                "Здесь можно будет поделиться с кем-нибудь непонятно чем",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    companion object {
        fun newInstance(searchRequest: Request?) = MapDeliveriesFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constant.KEY_REQUESTS_OBJECT, searchRequest)
            }
        }
    }
}
