package com.uzlov.valitova.justcargo.ui.fragments.order

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.geocoding.Address
import com.uzlov.valitova.justcargo.databinding.FragmentMapsBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseBottomFragment
import com.uzlov.valitova.justcargo.viemodels.GeocodingViewModel
import javax.inject.Inject

// created by Uzlov
// Фрагмент выбора местоположения отправления / пункта назначения
class SelectMapPositionsFragment private constructor() :
    BaseBottomFragment<FragmentMapsBinding>(
        FragmentMapsBinding::inflate) {

    val TAG = javaClass.simpleName

    interface ActionListener {
        fun select(address: Address?, latitude: String, longitude: String)
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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var model: GeocodingViewModel

    fun setActionListener(_actionListener: ActionListener) {
        actionListener = _actionListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val bottomSheet = bottomSheetDialog
                .findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = false
            }
        }
        return bottomSheetDialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
        model = viewModelFactory.create(GeocodingViewModel::class.java)
    }

    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.setOnCameraMoveStartedListener {
            upperMovePositionPicker()
        }

        googleMap.setOnCameraIdleListener {
            upperCancelMovePositionPicker()
            model.fetchGeocoding(
                lat = googleMap.cameraPosition.target.latitude.toString(),
                lng = googleMap.cameraPosition.target.longitude.toString()
            ).observe(viewLifecycleOwner, {
                actionListener?.select(it.address,
                    googleMap.cameraPosition.target.latitude.toString(),
                    googleMap.cameraPosition.target.longitude.toString())

                viewBinding.btnSetAddress.text = it.prettyAddress
            })
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

        arguments?.let {
            val title = it.getString(Constant.KEY_TITLE, getString(R.string.select_geopositions))
            viewBinding.sheetToolbar.title = title
        }

        viewBinding.btnSetAddress.setOnClickListener {
            dismiss()
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actionListener = null
    }
}