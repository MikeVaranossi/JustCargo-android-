package com.uzlov.valitova.justcargo.ui.fragments.order

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.databinding.FragmentMapsBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.viemodels.GeocodingViewModel
import javax.inject.Inject

// created by Uzlov
// Фрагмент выбора местоположения отправления / пункта назначения
class SelectMapPositionsFragment private constructor() :
    BaseFragment<FragmentMapsBinding>(
        FragmentMapsBinding::inflate) {

    val TAG: String = javaClass.simpleName

    interface ActionListener {
        fun select(address: String, latitude: Double, longitude: Double)
    }

    companion object {
        fun newInstance(title: String): SelectMapPositionsFragment {

            val fragment = SelectMapPositionsFragment().apply {
                arguments = bundleOf(Constant.KEY_TITLE to title)
            }
            return fragment
        }
    }

    private var actionListener: ActionListener? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var model: GeocodingViewModel

    fun setActionListener(_actionListener: ActionListener) {
        actionListener = _actionListener
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startGetLocation()
                } else {
                    Toast.makeText(requireContext(),
                        getString(R.string.you_denied_call_from_app),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
        model = viewModelFactory.create(GeocodingViewModel::class.java)
    }

    private val callback = OnMapReadyCallback { googleMap ->

        enableSelfPositions(googleMap)

        googleMap.setOnCameraMoveStartedListener {
            upperMovePositionPicker()
        }

        googleMap.setOnCameraIdleListener {
            upperCancelMovePositionPicker()
            model.fetchGeocoding(
                lat = googleMap.cameraPosition.target.latitude.toString(),
                lng = googleMap.cameraPosition.target.longitude.toString()
            ).observe(viewLifecycleOwner, {
                actionListener?.select(it.prettyAddress,
                    googleMap.cameraPosition.target.latitude,
                    googleMap.cameraPosition.target.longitude)

                viewBinding.btnSetAddress.text = it.prettyAddress
            })
        }

        if (checkLocationPermissions()) {
            // Разрешение уже есть, звоним
            startGetLocation()
        } else {
            requirePermissionsLocation()
        }
    }

    private fun enableSelfPositions(googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requirePermissionsLocation()
            return
        } else {
            googleMap.isMyLocationEnabled = true
        }
    }

    private fun upperCancelMovePositionPicker() {
        viewBinding.ivPicker.apply {
            animate().apply {
                duration = 100
            }.translationY(10F).start()
        }
    }

    private fun upperMovePositionPicker() {
        viewBinding.ivPicker.apply {
            animate().apply {
                duration = 100
            }.translationY(-10F).start()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewBinding.btnSetAddress.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun checkLocationPermissions(): Boolean {
        return if (!isGrantedAccessCoarseLocation() || !isGrantedAccessFineLocation()
        ) {
            requirePermissionsLocation()
            false
        } else {
            // Разрешение уже есть, звоним
            true
        }
    }

    private fun isGrantedAccessCoarseLocation(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun isGrantedAccessFineLocation(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requirePermissionsLocation() {
        when {
            isGrantedAccessCoarseLocation() || isGrantedAccessFineLocation() -> {
                startGetLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // show message
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }


    private fun startGetLocation() {
        Log.e(TAG, "startGetLocation:")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actionListener = null
    }
}
