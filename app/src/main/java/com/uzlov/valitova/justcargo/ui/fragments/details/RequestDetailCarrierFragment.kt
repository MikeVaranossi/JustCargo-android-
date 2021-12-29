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
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.app.create
import com.uzlov.valitova.justcargo.app.toRequestRemote
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentDetailCarrierLayoutBinding
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

    private var delivery: Delivery? = null

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

        authService.currentUser()?.phone?.let { phone ->
            // скрываем view со статусом заявки
            hideStateUI()

            if (phone.isNullOrEmpty() || phone.isBlank()) return@let

            deliveryViewModel.getDeliveryWithParam(idRequest, phone)
                .observe(viewLifecycleOwner, {
                    if (it == null) {
                        Log.e("TAG", "onViewCreated: null")
                    } else {
                        Log.e("TAG", "onViewCreated: not null")
                    }
                    it?.let {
                        showStateUI()
                        delivery = it.copy()
                        viewBinding.tvStatusDelivery.text = it.request?.status?.name
                    }
                })

            deliveryViewModel.getDeliveriesWithRequestID(idRequest)
                .observe(viewLifecycleOwner, {
                    Log.e(javaClass.simpleName, "WithRequest: $it")
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

            buttonTakeCargo.setOnClickListener {
                startBookingRequest()
            }

            buttonCancelCargo.setOnClickListener {
                startRemoveDelivery()
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun startRemoveDelivery() {
        authService.currentUser()?.let { user ->
            delivery?.let { _delivery ->
                deliveryViewModel.removeDelivery(_delivery)
                hideStateUI()
            }
        }
    }

    // создание "Доставки", отправка её на бэк /
    private fun startBookingRequest() {
        authService.currentUser()?.let { user ->
            if (request != null) {
                delivery = Delivery().create(user, request!!)
                deliveryViewModel.addDelivery(delivery!!)
            } else {
                delivery = Delivery().create(user,
                    requestLocal?.toRequestRemote()!!)
                deliveryViewModel.addDelivery(delivery!!)
            }
            showStateUI()
        }
    }

    // срабатывает тогда когда мы отправили бронь на заявку
    private fun showStateUI() {
        with(viewBinding) {
            // показываем статус брони
            tvLabelStateDelivery.visibility = View.VISIBLE
            tvStatusDelivery.visibility = View.VISIBLE

            // показываем кнопку отказаться
            buttonCancelCargo.visibility = View.VISIBLE

            // скрываем "взять груз"
            buttonTakeCargo.visibility = View.GONE
        }
    }

    // срабатывает если мы не отправляли бронирования
    private fun hideStateUI() {
        with(viewBinding) {
            // скрываем статус бронирования
            tvLabelStateDelivery.visibility = View.INVISIBLE
            tvStatusDelivery.visibility = View.INVISIBLE

            // скрываем кнопку взять груз
            buttonCancelCargo.visibility = View.GONE

            // показываем кнопку взять груз
            buttonTakeCargo.visibility = View.VISIBLE
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