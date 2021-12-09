package com.uzlov.valitova.justcargo.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.Constant.Companion.MY_PERMISSIONS_REQUEST_CALL_PHONE
import com.uzlov.valitova.justcargo.databinding.FragmentDetailLayoutBinding

import com.uzlov.valitova.justcargo.data.net.Request

/*
* Фрагмент отображает детальную информацию о заявке на перевозку груза.
* */
class RequestDetailFragment :
    BaseFragment<FragmentDetailLayoutBinding>(FragmentDetailLayoutBinding::inflate) {

    private var request: Request? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            request = it.getParcelable(Constant.KEY_REQUESTS_OBJECT)
        }
    }

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
                request?.owner?.phone?.let { numberPhone -> callTo(numberPhone) }
            }

            fabStartChat.setOnClickListener {
                Toast.makeText(requireContext(),
                    getString(R.string.where_is_chat),
                    Toast.LENGTH_SHORT).show()
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = request?.shortInfo
            it.setDisplayHomeAsUpEnabled(true)
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
            RequestDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constant.KEY_REQUESTS_OBJECT, request)
                }
            }
    }
}