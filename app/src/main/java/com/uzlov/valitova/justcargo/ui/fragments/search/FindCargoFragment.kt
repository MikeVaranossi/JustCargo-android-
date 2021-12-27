package com.uzlov.valitova.justcargo.ui.fragments.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.app.toFavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentFindCargoBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.RVHomeCarrierAdapter
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailCarrierFragment
import com.uzlov.valitova.justcargo.viemodels.FavoritesRequestsViewModel
import com.uzlov.valitova.justcargo.viemodels.RequestsViewModel
import javax.inject.Inject

class FindCargoFragment : BaseFragment<FragmentFindCargoBinding>(
    FragmentFindCargoBinding::inflate
) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var model: RequestsViewModel
    lateinit var modelFavorites: FavoritesRequestsViewModel

    private var requests: List<Request> = arrayListOf()
    private var searchRequest: Request = Request()

    private val adapter by lazy {
        RVHomeCarrierAdapter(listenerOnClickCargoItem)
    }

    private val listenerOnClickCargoItem = object : RVHomeCarrierAdapter.OnItemClickListener {
        override fun click(request: Request) {
            openFragment(RequestDetailCarrierFragment.newInstance(request))

        }

        override fun addToFavorite(request: Request) {
            modelFavorites.putRequest(request.toFavoriteRequestLocal())
        }

        override fun removeFromFavorite(request: Request) {
            modelFavorites.removeRequest(request.toFavoriteRequestLocal())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        model = viewModelFactory.create(RequestsViewModel::class.java)
        modelFavorites = viewModelFactory.create(FavoritesRequestsViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.find_cargo)
            it.setDisplayHomeAsUpEnabled(true)
        }
        viewBinding.recyclerViewFindCarrier.adapter = adapter
        viewBinding.recyclerViewFindCarrier.setHasFixedSize(true)
        viewBinding.recyclerViewFindCarrier.setItemViewCacheSize(10)

        arguments?.let {
            it.getParcelable<Request>(Constant.KEY_REQUESTS_OBJECT)?.let { _request ->
                searchRequest = _request.copy()
            }
        }
        if (!searchRequest.departure.isNullOrEmpty() && !searchRequest.destination.isNullOrEmpty()) {
            getSearchLatLng(
                searchRequest.departure!!,
                searchRequest.destination!!,
                searchRequest.deliveryTime!!
            )
        }
        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_fragment_find
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
        initListeners()
    }

    private fun checkRV(data: List<Request>) {
        adapter.setData(data)
        if (data.isEmpty()) {
            viewBinding.textViewFindCargo.text = "Ничего не нашлось("
        } else {
            viewBinding.recyclerViewFindCarrier.visibility = View.VISIBLE
            viewBinding.textViewFindCargo.text =
                getString(R.string.text_label_find_cargo, data.size)
        }
    }


    private fun getSearchLatLng(from: String, to: String, time: Long) {
        if (time == 0L){
            model.searchRequest(from, to)?.observe(this, {
                requests = it
                checkRV(it)
            })
        } else {
            model.searchRequest(from, to, time)?.observe(this, {
                requests = it
                checkRV(it)
            })
        }

        modelFavorites.getIDList().observe(this, {
            adapter.setIDs(it)
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

    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initListeners() {
        with(viewBinding) {
            toggleGroup.addOnButtonCheckedListener { _, checkedId, _ ->
                if (checkedId == R.id.tb_list) {
                    recyclerViewFindCarrier.visibility = View.VISIBLE
                    mapFragmentCarrier.visibility = View.GONE
                } else {
                    recyclerViewFindCarrier.visibility = View.GONE
                    mapFragmentCarrier.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        fun newInstance(searchRequest: Request?) = FindCargoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constant.KEY_REQUESTS_OBJECT, searchRequest)
            }
        }
    }
}