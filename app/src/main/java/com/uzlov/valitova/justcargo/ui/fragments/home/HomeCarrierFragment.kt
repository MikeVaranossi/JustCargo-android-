package com.uzlov.valitova.justcargo.ui.fragments.home

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.app.toFavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentHomeCarrierBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.RVHomeCarrierAdapter
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailCarrierFragment
import com.uzlov.valitova.justcargo.ui.fragments.SearchFragment
import com.uzlov.valitova.justcargo.viemodels.FavoritesRequestsViewModel
import com.uzlov.valitova.justcargo.viemodels.RequestsViewModel
import javax.inject.Inject


class HomeCarrierFragment : BaseFragment<FragmentHomeCarrierBinding>(
    FragmentHomeCarrierBinding::inflate
) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var model: RequestsViewModel
    lateinit var modelFavorites: FavoritesRequestsViewModel

    private var requests: List<Request> = arrayListOf()
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
        setHasOptionsMenu(true)
        loadData()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.label_home_carrier_fragment)
            it.setDisplayHomeAsUpEnabled(false)

        }
        viewBinding.recyclerViewHomeCarrier.adapter = adapter
        viewBinding.recyclerViewHomeCarrier.setItemViewCacheSize(100)
        initListeners()


        val mapFragment = childFragmentManager.findFragmentById(
            R.id.home_map_fragment_find
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
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds.build(),
                            50,
                            50,
                            15
                        )
                    )
                    addMarkers(googleMap)
                }

            }
            googleMap.setOnMarkerClickListener { marker ->
                openFragment(RequestDetailCarrierFragment.newInstance(requests[marker.zIndex.toInt()]))
                return@setOnMarkerClickListener false
            }
        }

    }

    private fun initListeners() {
        with(viewBinding) {
            toggleGroup.addOnButtonCheckedListener { _, checkedId, _ ->
                if (checkedId == R.id.tb_list) {
                    recyclerViewHomeCarrier.visibility = View.VISIBLE
                    homeMapFragmentCarrier.visibility = View.GONE
                } else {
                    recyclerViewHomeCarrier.visibility = View.GONE
                    homeMapFragmentCarrier.visibility = View.VISIBLE
                }
            }
            buttonFindCargo.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SearchFragment.newInstance())
                    .commit()
            }
        }
    }

    private fun loadData() {
        model.getRequests()?.observe(this, {
            requests = it
            checkRV(it)
        })
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

    private fun checkRV(data: List<Request>) {
        adapter.setData(data)
        if (data.isEmpty()) {
            viewBinding.imageViewCarrierHomeEmpty.visibility = View.VISIBLE
            viewBinding.texViewLabelEmpty.visibility = View.VISIBLE
            viewBinding.recyclerViewHomeCarrier.visibility = View.GONE
            viewBinding.buttonFindCargo.visibility = View.GONE
            viewBinding.textViewFindCargo.visibility = View.GONE
            loadImage(R.drawable.image_home_carrier_fragment, viewBinding.imageViewCarrierHomeEmpty)
        } else {
            viewBinding.imageViewCarrierHomeEmpty.visibility = View.GONE
            viewBinding.texViewLabelEmpty.visibility = View.GONE
            viewBinding.recyclerViewHomeCarrier.visibility = View.VISIBLE
            viewBinding.buttonFindCargo.visibility = View.VISIBLE
            viewBinding.textViewFindCargo.visibility = View.VISIBLE
            viewBinding.textViewFindCargo.text =
                getString(R.string.text_label_find_cargo, data.size)
        }
    }


    private fun loadImage(image: Int, container: ImageView) {
        view?.let {
            Glide
                .with(it.context)
                .load(image)
                .into(container)
        }
    }

    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .hide(this)
            .add(R.id.fragment_container, fragment, "")
            .show(fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() =
            HomeCarrierFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}