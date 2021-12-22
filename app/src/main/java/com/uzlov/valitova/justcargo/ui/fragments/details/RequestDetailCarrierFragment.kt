package com.uzlov.valitova.justcargo.ui.fragments.details

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.app.create
import com.uzlov.valitova.justcargo.app.toRequestRemote
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.databinding.FragmentDetailCarrierLayoutBinding

import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.viemodels.DeliveryViewModel
import com.uzlov.valitova.justcargo.viemodels.ViewModelFactory
import javax.inject.Inject

/*
* Фрагмент отображает детальную информацию о заявке на перевозку груза.
* */
class RequestDetailCarrierFragment :
    BaseFragment<FragmentDetailCarrierLayoutBinding>(FragmentDetailCarrierLayoutBinding::inflate) {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var request: Request? = null
    private var requestLocal: FavoriteRequestLocal? = null

    @Inject
    lateinit var modelFactory: ViewModelFactory
    lateinit var deliveryViewModel: DeliveryViewModel

    @Inject
    lateinit var authService: AuthService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    callToUser()
                } else {
                    Toast.makeText(requireContext(),
                        getString(R.string.you_denied_call_from_app),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set dependencies
        requireContext().appComponent.inject(this)
        deliveryViewModel = modelFactory.create(DeliveryViewModel::class.java)

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
        } else if (requestLocal != null) {
            updateUI(requestLocal)
        }

        val idRequest: Long = request?.id ?: requestLocal?.id ?: 0L

        // скрываем view со статусом заявки
        hideStateUI()

        authService.currentUser()?.let {
            deliveryViewModel.getDeliveryWithParam(idRequest,it.phone ?: "")?.observe(viewLifecycleOwner, {
                it?.let {
                    showStateUI()
                    viewBinding.tvStatusDelivery.text = it.request?.status?.name
                    viewBinding.buttonTakeCargo.visibility = View.GONE
                }
            })
        }
    }

    private fun updateUI(request: Request?) {
        request?.let {
            (requireActivity() as AppCompatActivity).supportActionBar?.let {
                it.title = request.shortInfo
            }
            with(viewBinding) {
                tvCargoName.text = it.shortInfo
                textViewFromTo.text = "   ${it.departure} - ${it.destination}   "
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
                textViewFromTo.text = "   ${it.departure} - ${it.destination}   "
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

            buttonTakeCargo.setOnClickListener {
                startBookingRequest()
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // создание "Доставки", отправка её на бэк /
    private fun startBookingRequest() {
        authService.currentUser()?.let { user ->
            if (request != null) {
                deliveryViewModel.addDelivery(Delivery().create(user, request!!))
            } else {
                deliveryViewModel.addDelivery(Delivery().create(user,
                    requestLocal?.toRequestRemote()!!))
            }

            showStateUI()
        }
    }

    private fun showStateUI() {
        with(viewBinding) {
            tvLabelStateDelivery.visibility = View.VISIBLE
            tvStatusDelivery.visibility = View.VISIBLE
        }
    }

    private fun hideStateUI() {
        with(viewBinding) {
            tvLabelStateDelivery.visibility = View.INVISIBLE
            tvStatusDelivery.visibility = View.INVISIBLE
        }
    }

    // начало звонка
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

    // звонок пользователю
    private fun callToUser() {
        request?.owner?.phone?.let {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$it"))
            startActivity(intent)
        }
    }

    // проверка разрешений
    private fun checkCallToPhonePermissions(): Boolean {
        return if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CALL_PHONE)
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
            RequestDetailCarrierFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constant.KEY_REQUESTS_OBJECT, request)
                }
            }

        fun newInstance(request: FavoriteRequestLocal) =
            RequestDetailCarrierFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constant.KEY_REQUESTS_LOCAL_OBJECT, request)
                }
            }
    }
}