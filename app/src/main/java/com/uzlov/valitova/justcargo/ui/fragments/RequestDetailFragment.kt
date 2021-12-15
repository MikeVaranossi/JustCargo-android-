package com.uzlov.valitova.justcargo.ui.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.databinding.FragmentDetailLayoutBinding

import com.uzlov.valitova.justcargo.data.net.Request

/*
* Фрагмент отображает детальную информацию о заявке на перевозку груза.
* */
class RequestDetailFragment :
    BaseFragment<FragmentDetailLayoutBinding>(FragmentDetailLayoutBinding::inflate) {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var request: Request? = null
    private var requestLocal: FavoriteRequestLocal? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted){
                    callToUser()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.you_denied_call_from_app), Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            request = it.getParcelable(Constant.KEY_REQUESTS_OBJECT)
            requestLocal = it.getParcelable(Constant.KEY_REQUESTS_LOCAL_OBJECT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        if (request != null) {
            updateUI(request)
        } else {
            updateUI(requestLocal)
        }
    }

    private fun updateUI(request: Request?) {
        request?.let {
            (requireActivity() as AppCompatActivity).supportActionBar?.let {
                it.title = request.shortInfo
            }
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

    private fun updateUI(request: FavoriteRequestLocal?) {
        request?.let {
            (requireActivity() as AppCompatActivity).supportActionBar?.let {
                it.title = request.shortInfo
            }
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
                request?.owner?.phone?.let { continueCall() }
            }

            fabStartChat.setOnClickListener {
                Toast.makeText(requireContext(),
                    getString(R.string.where_is_chat),
                    Toast.LENGTH_SHORT).show()
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun continueCall() {
        request?.owner?.phone?.let {
            if (it.trim().isEmpty()) {
                Toast.makeText(requireContext(),
                    getString(R.string.incorrect_phone_number),
                    Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (checkCallToPhonePermissions()) {
            // Разрешение уже есть, звоним
            callToUser()
        } else {
            requirePermissionsCallToPhone()
        }
    }

    private fun callToUser() {
        request?.owner?.phone?.let {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$it"))
            startActivity(intent)
        }
    }

    private fun checkCallToPhonePermissions(): Boolean {
        return if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requirePermissionsCallToPhone()
            false
        } else {
            // Разрешение уже есть, звоним
            true
        }
    }

    private fun requirePermissionsCallToPhone() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED -> {
                callToUser()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {

            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CALL_PHONE)
            }
        }
    }

    companion object {
        fun newInstance(request: Request) =
            RequestDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constant.KEY_REQUESTS_OBJECT, request)
                }
            }

        fun newInstance(request: FavoriteRequestLocal) =
            RequestDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constant.KEY_REQUESTS_LOCAL_OBJECT, request)
                }
            }
    }
}