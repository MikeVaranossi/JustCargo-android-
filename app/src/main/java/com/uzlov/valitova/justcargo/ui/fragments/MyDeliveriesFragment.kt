package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.databinding.MyDeliveriesProfileLayoutBinding
import com.uzlov.valitova.justcargo.model.entities.Delivery
import com.uzlov.valitova.justcargo.viemodels.DeliveryViewModel
import javax.inject.Inject

class MyDeliveriesFragment : BaseFragment<MyDeliveriesProfileLayoutBinding>(
    MyDeliveriesProfileLayoutBinding::inflate){

    private val TAG: String = javaClass.simpleName

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var model: DeliveryViewModel

    companion object {
        fun newInstance(): MyDeliveriesFragment {
            return MyDeliveriesFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        model = viewModelFactory.create(DeliveryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.my_deliveries)
            it.setDisplayHomeAsUpEnabled(true)
        }
        initListeners()
        loadData()
    }

    private fun initListeners() {
        viewBinding.refreshDataLayout.setOnRefreshListener {
            viewBinding.refreshDataLayout.isRefreshing = true
            loadData()
        }
    }

    private fun loadData() {
        model.getDeliveries()?.observe(this, {
            updateUI(it)
        })
    }

    private fun updateUI(data: List<Delivery>) {
        val filteredData = filterData(data)
        if (filteredData.isEmpty()) {
            viewBinding.tvNotAvailableDeliveries.visibility = View.VISIBLE
            viewBinding.rvDeliveries.visibility = View.GONE
        } else {
            viewBinding.tvNotAvailableDeliveries.visibility = View.GONE
            viewBinding.rvDeliveries.visibility = View.VISIBLE
        }
        viewBinding.refreshDataLayout.isRefreshing = false
    }

    private fun filterData(data: List<Delivery>): List<Delivery> {
        val phone = "89992001122" // для тестов
        return data.filter {
            it.trip?.carrier?.phone == phone
        }
    }
}