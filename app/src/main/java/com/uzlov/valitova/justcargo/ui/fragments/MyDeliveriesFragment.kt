package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.databinding.MyDeliveriesProfileLayoutBinding
import com.uzlov.valitova.justcargo.viemodels.DeliveryViewModel
import javax.inject.Inject

class MyDeliveriesFragment : BaseFragment<MyDeliveriesProfileLayoutBinding>(
    MyDeliveriesProfileLayoutBinding::inflate){

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

        loadData()

    }

    private fun loadData() {
        model.getDeliveries()?.observe(this, {

        })
    }

}