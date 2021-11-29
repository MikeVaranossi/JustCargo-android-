package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.MyDeliveriesProfileLayoutBinding

class MyDeliveriesFragment private constructor(): Fragment() {

    private var _viewBinding: MyDeliveriesProfileLayoutBinding? = null
    private val viewBinding get() = _viewBinding!!

    companion object {
        fun newInstance(): MyDeliveriesFragment {
            return MyDeliveriesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MyDeliveriesProfileLayoutBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.my_deliveries)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }
    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}