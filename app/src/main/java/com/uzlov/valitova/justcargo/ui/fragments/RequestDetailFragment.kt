package com.uzlov.valitova.justcargo.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.Constant.Companion.MY_PERMISSIONS_REQUEST_CALL_PHONE
import com.uzlov.valitova.justcargo.databinding.FragmentDetailLayoutBinding

import com.uzlov.valitova.justcargo.model.entities.Request

/*
* Фрагмент отображает детальную информацию о заявке на перевозку груза.
* */
class RequestDetailFragment private constructor() : Fragment() {

    private var _viewBinding: FragmentDetailLayoutBinding? = null
    private val viewBinding get() = _viewBinding!!
    private var request: Request? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null && savedInstanceState.isEmpty) {
            request = savedInstanceState.getParcelable(Constant.KEY_DELIVERY_OBJECT)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentDetailLayoutBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        updateUI(request)

    }

    private fun updateUI(delivery: Request?) {
        delivery?.let {
            with(viewBinding) {
                tvCargoName.text = it.shortInfo
                textViewFromTo.text = "${it.departure} ${it.destination}"
                tvCommentCargo.text = it.description
                tvCostDelivery.text = it.cost.toString()
                tvHeightCargoValue.text = it.height.toString()
                tvWidthCargoValue.text = it.width.toString()
                tvLengthCargoValue.text = it.length.toString()
            }
        }
    }

    private fun initListeners() {
        with(viewBinding) {
            fabCall.setOnClickListener {
                Toast.makeText(requireContext(),
                    getString(R.string.where_is_chat),
                    Toast.LENGTH_SHORT).show()
            }

            fabStartChat.setOnClickListener {
                request?.owner?.phone?.let { numberPhone -> callTo(numberPhone) }
            }
        }
    }

    private fun callTo(number: String) {
        if (number.trim().isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.incorrect_phone_number),
                Toast.LENGTH_SHORT).show()
            return
        }

        if (checkCallToPhonePermissions()) {
            // Разрешение уже есть, звоним
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
            startActivity(intent)
        } else {
            requirePermissionsCallToPhone()
        }
    }

    private fun checkCallToPhonePermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requirePermissionsCallToPhone()
            return false
        } else {
            // Разрешение уже есть, звоним
            return true
        }
    }

    private fun requirePermissionsCallToPhone() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CALL_PHONE
            )
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE),
                MY_PERMISSIONS_REQUEST_CALL_PHONE
            )
        }
    }

    companion object {
        fun newInstance(request: Request) =
            HomeFragment().apply {
                arguments = bundleOf(Constant.KEY_REQUESTS_OBJECT to request)
            }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}