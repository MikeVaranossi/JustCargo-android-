package com.uzlov.valitova.justcargo.ui.fragments.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.Constant.Companion.STATE_IN_PROGRESS
import com.uzlov.valitova.justcargo.app.Constant.Companion.STATE_IN_PROGRESS_MESSAGE
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentDetailSenderLayoutBinding
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

    @Inject
    lateinit var modelFactory: ViewModelFactory
    lateinit var deliveryViewModel: DeliveryViewModel

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
            request.id?.let { it ->
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
                if (item != request) item.id?.let { it -> deliveryViewModel.removeDelivery(it) }
            }
        }
    }

    private val adapter by lazy {
        RVUsersRequestAdapter(callback).apply {}
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
            ?.observe(viewLifecycleOwner, {
                it?.let {
                    //если в данный код не попали значит перевозчик не найден
                    adapter.setDelivery(it)
                    showStateUI(it)

                    //если на первой заявке статус STATE_IN_PROGRESS значит бронь подтверждена
                    if (it.size == 1){
                        val status = it[0].request?.status?.id
                        if (status == STATE_IN_PROGRESS)
                            showProfileCarrierUI()
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
            deliverys?.get(0)!!.id?.let { it1 ->
                deliveryViewModel.removeDelivery(it1)
                hideStateUI()
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun callToUser() {
        request?.owner?.phone?.let {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$it"))
            startActivity(intent)
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
            fabStartChat.visibility = View.VISIBLE
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
            // возврашаем к значениям по умолчаниюtvLabelStateDelivery
            tvStatusDelivery.visibility = View.VISIBLE
            tvStatusDelivery.text = getString(R.string.not_found)

            btnComplete.visibility = View.GONE
            btnCancel.visibility = View.GONE
            rvDeliveries.visibility = View.GONE
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