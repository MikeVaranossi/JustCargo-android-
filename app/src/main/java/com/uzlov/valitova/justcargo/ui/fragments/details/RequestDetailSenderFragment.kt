package com.uzlov.valitova.justcargo.ui.fragments.details

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
import com.uzlov.valitova.justcargo.app.Constant.Companion.STATE_COMPLETE
import com.uzlov.valitova.justcargo.app.Constant.Companion.STATE_IN_PROGRESS
import com.uzlov.valitova.justcargo.app.Constant.Companion.STATE_IN_PROGRESS_MESSAGE
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentDetailSenderLayoutBinding
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.RVUsersRequestAdapter
import com.uzlov.valitova.justcargo.ui.fragments.profile.DetailsProfileCarrierFragment
import com.uzlov.valitova.justcargo.viemodels.DeliveryViewModel
import com.uzlov.valitova.justcargo.viemodels.ViewModelFactory
import javax.inject.Inject

/*
* Фрагмент отображает детальную информацию о заявке на перевозку груза.
* */
class RequestDetailSenderFragment :
    BaseFragment<FragmentDetailSenderLayoutBinding>(FragmentDetailSenderLayoutBinding::inflate) {

    private var request: Request? = null
    private var requestLocal: FavoriteRequestLocal? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @Inject
    lateinit var modelFactory: ViewModelFactory
    lateinit var deliveryViewModel: DeliveryViewModel

    @Inject
    lateinit var requestsUseCases: RequestsUseCases

    private var deliverys: List<Delivery>? = null

    private val callback = object : RVUsersRequestAdapter.OnItemClickListener {
        override fun click(request: Delivery) {
            val manager = requireActivity().supportFragmentManager
            manager.apply {
                beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container,
                        DetailsProfileCarrierFragment.newInstance(request.trip?.carrier))
                    .commit()
            }
        }

        override fun reject(request: Delivery) {
            request.let { it ->
                deliveryViewModel.removeDelivery(it)
                hideStateUI()
            }
        }

        override fun accept(request: Delivery) {
            request.request?.status?.id = STATE_IN_PROGRESS
            request.request?.status?.name = STATE_IN_PROGRESS_MESSAGE
            request.status?.id = STATE_IN_PROGRESS
            deliveryViewModel.addDelivery(request)
            showProfileCarrierUI()

            deliverys?.forEach { item ->
                if (item != request) item.let { it -> deliveryViewModel.removeDelivery(it) }
            }
        }
    }

    private val adapter by lazy {
        RVUsersRequestAdapter(callback).apply {}
    }

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
        } else {
            updateUI(requestLocal)
        }
        viewBinding.rvDeliveries.adapter = adapter

        val idRequest: Long = request?.id ?: requestLocal?.id ?: 0L
        //val statusRequest = request?.status?.id ?: requestLocal?.status.id? ?: 0L
        hideStateUI()

        deliveryViewModel.getDeliveriesWithRequestID(idRequest)
            .observe(viewLifecycleOwner, {
                it?.let {
                    //если в данный код не попали значит перевозчик не найден
                    adapter.setDelivery(it)
                    showStateUI(it)

                    //если на первой заявке статус STATE_IN_PROGRESS значит бронь подтверждена
                    if (it.size == 1) {
                        val status = it[0].request?.status?.id
                        if (status == STATE_IN_PROGRESS)
                            showProfileCarrierUI()
                        if (status == STATE_COMPLETE)
                            completedDelivery()
                    }
                    deliverys = it
                }
            })
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
        //Отменить
        viewBinding.btnCancel.setOnClickListener {
            //если нет заявок на перевозку по кнопке отменить происходит удаление заявки
            //иначе отменяется перевозка
            if (deliverys == null) {
                val idRequest: Long = request?.id ?: requestLocal?.id ?: 0L
                requestsUseCases.removeRequests(idRequest)
                parentFragmentManager.popBackStack()
            } else {
                deliverys?.get(0)?.let { it1 ->
                    deliveryViewModel.removeDelivery(it1)
                    hideStateUI()
                }
            }
        }

        viewBinding.btnComplete.setOnClickListener {
            if (deliverys?.size == 1) {
                deliverys?.firstOrNull()?.status?.id = STATE_COMPLETE
                deliverys?.firstOrNull()?.request?.status?.id = STATE_COMPLETE
                deliverys?.firstOrNull()?.request?.status?.name =
                    getString(R.string.application_completed)
                deliveryViewModel.addDelivery(deliverys!![0])
                completedDelivery()
            }
        }
        viewBinding.fabCall.setOnClickListener {
            deliverys?.get(0)?.trip?.carrier?.phone?.let { continueCall() }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun continueCall() {
        deliverys?.get(0)?.trip?.carrier?.phone?.let {
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
        deliverys?.get(0)?.trip?.carrier?.phone?.let {
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

    // срабатывает тогда когда отправлена бронь на заявку
    private fun showStateUI(list: List<Delivery>) {
        with(viewBinding) {
            if (list.isNullOrEmpty()) {
                // заявок на бронирование нет, пишем что перевозчик не найден
                tvStatusDelivery.visibility = View.VISIBLE
                tvStatusDelivery.text = getString(R.string.not_found)
            } else {
                // рисуем список заявок
                tvStatusDelivery.visibility = View.GONE
                rvDeliveries.visibility = View.VISIBLE
            }
        }
    }

    // срабатывает когда бронь подтвердили
    private fun showProfileCarrierUI() {

        with(viewBinding) {
            //показываем кнопки звонка и чата
            fabCall.visibility = View.VISIBLE

            tvLabelStateDelivery.visibility = View.VISIBLE
            tvStatusDelivery.visibility = View.VISIBLE
            tvStatusDelivery.text = getString(R.string.wait_delivery_cargo)

            rvDeliveries.visibility = View.GONE
            // показываем нужные кнопки
            btnComplete.visibility = View.VISIBLE
            btnCancel.visibility = View.VISIBLE
        }
    }

    // срабатывает когда отказали в бронировании
    private fun hideStateUI() {
        with(viewBinding) {
            // возврашаем к значениям по умолчанию tvLabelStateDelivery
            tvStatusDelivery.visibility = View.VISIBLE
            tvStatusDelivery.text = getString(R.string.not_found)

            btnComplete.visibility = View.GONE
            btnCancel.visibility = View.VISIBLE
            rvDeliveries.visibility = View.GONE
        }
    }

    //заявка выполнена
    private fun completedDelivery() {

        with(viewBinding) {
            fabCall.visibility = View.GONE

            tvLabelStateDelivery.visibility = View.VISIBLE
            tvStatusDelivery.visibility = View.VISIBLE
            tvStatusDelivery.text = getString(R.string.application_completed)

            rvDeliveries.visibility = View.GONE
            // показываем нужные кнопки
            btnComplete.visibility = View.GONE
            btnCancel.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(request: Request) =
            RequestDetailSenderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constant.KEY_REQUESTS_OBJECT, request)
                }
            }

        fun newInstance(request: FavoriteRequestLocal) =
            RequestDetailSenderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constant.KEY_REQUESTS_LOCAL_OBJECT, request)
                }
            }
    }
}