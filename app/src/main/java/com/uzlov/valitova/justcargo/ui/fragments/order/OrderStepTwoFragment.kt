package com.uzlov.valitova.justcargo.ui.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentOrderStepTwoBinding

class OrderStepTwoFragment: Fragment() {

    private var _viewBinding: FragmentOrderStepTwoBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOrderStepTwoBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.label_order_step_two)
            it.setDisplayHomeAsUpEnabled(true)
        }
        viewBinding.calendarView.minDate = System.currentTimeMillis() - 1000
        viewBinding.buttonNextStep.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrderStepThreeFragment.newInstance())
                .commit()
        }
    }

    companion object {
        fun newInstance() = OrderStepTwoFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}