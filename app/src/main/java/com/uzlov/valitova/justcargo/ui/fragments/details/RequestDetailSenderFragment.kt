package com.uzlov.valitova.justcargo.ui.fragments.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.Constant.Companion.STATE_IN_PROGRESS
import com.uzlov.valitova.justcargo.app.Constant.Companion.STATE_IN_PROGRESS_MESSAGE
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.app.toFavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.databinding.FragmentDetailSenderLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.RVLocalRequestAdapter
import com.uzlov.valitova.justcargo.ui.fragments.RVUsersRequestAdapter
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

    private var delivery: Delivery? = null

    private val callback = object : RVUsersRequestAdapter.OnItemClickListener{
        override fun click(request: Delivery) {
            //openFragment(RequestDetailCarrierFragment.newInstance(request))
        }

        override fun reject(request: Delivery) {

            request.id?.let { it -> deliveryViewModel.removeDelivery(it)
                hideStateUI()
            }

            //favouritesVModel.putRequest(request.toFavoriteRequestLocal())
        }

        override fun accept(request: Delivery) {

            request.request?.status?.id = STATE_IN_PROGRESS
            request.request?.status?.name = STATE_IN_PROGRESS_MESSAGE
            deliveryViewModel.addDelivery(request)
            showProfileCarrierUI()

            //favouritesVModel.removeRequest(request.toFavoriteRequestLocal())
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
        deliveryViewModel.getDeliveriesWithRequestID(idRequest)
            ?.observe(viewLifecycleOwner, {
                it?.let {

                    adapter.setDelivery(it)
                    showStateUI()


                    /*delivery = it[0].copy()
                    val status = it[0].request?.status?.name
                    if (status == "Открыта"){

                    }
                    if (status == "Подтверждён"){
                        showProfileCarrierUI()
                    }*/
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
        viewBinding.buttonEditCargo.setOnClickListener {
            Toast.makeText(requireContext(),
                getString(R.string.where_is_chat),
                Toast.LENGTH_SHORT).show()
        }

        //Отменить
        viewBinding.btnCancel.setOnClickListener {
            if (delivery != null){
                delivery!!.id?.let { it1 -> deliveryViewModel.removeDelivery(it1)
                    hideStateUI()
                }
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
    private fun showStateUI() {
        with(viewBinding) {
            tvLabelStateDelivery.visibility = View.VISIBLE
            tvStatusDelivery.visibility = View.VISIBLE
            tvStatusDelivery.text = getString(R.string.wait_delivery_cargo)

            tvLabelStateDelivery.visibility = View.GONE
            tvStatusDelivery.visibility = View.GONE
            // показываем нужные кнопки
            btnComplete.visibility = View.GONE
            btnCancel.visibility = View.GONE

            buttonEditCargo.visibility = View.GONE
        }
    }

    // срабатывает когда бронь подтвердили
    private fun showProfileCarrierUI() {
        with(viewBinding) {
            rvDeliveries.visibility = View.GONE
            // показываем нужные кнопки
            btnComplete.visibility = View.VISIBLE
            btnCancel.visibility = View.VISIBLE

            buttonEditCargo.visibility = View.GONE
        }
    }

    // срабатывает когда отказали в бронировании
    private fun hideStateUI() {
        with(viewBinding) {
            // возврашаем к значениям по умолчанию
            tvLabelStateDelivery.visibility = View.VISIBLE
            tvStatusDelivery.visibility = View.VISIBLE
            tvStatusDelivery.text = getString(R.string.not_found)

            btnComplete.visibility = View.GONE
            btnCancel.visibility = View.GONE
            rvDeliveries.visibility = View.GONE
            buttonEditCargo.visibility = View.VISIBLE
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