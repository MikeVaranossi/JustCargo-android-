package com.uzlov.valitova.justcargo.ui.fragments.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentDetailSenderLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
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

        val idRequest: Long = request?.id ?: requestLocal?.id ?: 0L
        deliveryViewModel.getDeliveriesWithRequestID(idRequest)
            ?.observe(viewLifecycleOwner, {
                it?.let {
                    delivery = it[0].copy()
                    val status = it[0].request?.status?.name
                    if (status == "Открыта"){
                        showStateUI()
                    }
                    if (status == "Подтверждён"){
                        showProfileCarrierUI()
                    }
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

        viewBinding.btnConfirm.setOnClickListener {
            if (delivery != null){
                delivery!!.request?.status?.name = "Подтверждён"
                deliveryViewModel.addDelivery(delivery!!)
                showProfileCarrierUI()
            }
        }

        //Отказать грузоперевозчику
        viewBinding.btnDeny.setOnClickListener {
            if (delivery != null){
                delivery!!.id?.let { it1 -> deliveryViewModel.removeDelivery(it1)
                    hideStateUI()
                }
            }
        }

        //Отменить
        viewBinding.btnCancel.setOnClickListener {
            if (delivery != null){
                delivery!!.id?.let { it1 -> deliveryViewModel.removeDelivery(it1)
                    hideStateUI()
                }
            }
        }

        //просмотр профиля
        viewBinding.tvLinkProfile.setOnClickListener {
            if (delivery != null) {
                delivery!!.trip?.carrier?.let { it1 ->
                    /*parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()*/
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
            tvStatusDelivery.text = "Груз забронирован"
            // показываем статус брони
            tvLabelStateDelivery.visibility = View.VISIBLE
            tvStatusDelivery.visibility = View.VISIBLE
            tvLinkProfile.visibility = View.VISIBLE

            // показываем нужные кнопки
            btnComplete.visibility = View.GONE
            btnCancel.visibility = View.GONE

            btnConfirm.visibility = View.VISIBLE
            btnDeny.visibility = View.VISIBLE

            buttonEditCargo.visibility = View.GONE
        }
    }

    // срабатывает когда бронь подтвердили
    private fun showProfileCarrierUI() {
        with(viewBinding) {
            tvStatusDelivery.text = "Подтверждён. Ждите доставки груза"
            tvLabelStateDelivery.visibility = View.VISIBLE
            tvStatusDelivery.visibility = View.VISIBLE
            tvLinkProfile.visibility = View.INVISIBLE

            // показываем нужные кнопки
            btnComplete.visibility = View.VISIBLE
            btnCancel.visibility = View.VISIBLE

            btnConfirm.visibility = View.GONE
            btnDeny.visibility = View.GONE

            buttonEditCargo.visibility = View.GONE
        }
    }

    // срабатывает когда отказали в бронировании
    private fun hideStateUI() {
        with(viewBinding) {
            // возврашаем к значениям по умолчанию
            tvLabelStateDelivery.visibility = View.GONE
            tvStatusDelivery.visibility = View.GONE
            tvLinkProfile.visibility = View.GONE
            btnComplete.visibility = View.GONE
            btnCancel.visibility = View.GONE
            btnConfirm.visibility = View.GONE
            btnDeny.visibility = View.GONE

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