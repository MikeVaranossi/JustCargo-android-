package com.uzlov.valitova.justcargo.ui.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentOrderCreatedBinding
import com.uzlov.valitova.justcargo.ui.fragments.home.HomeSenderFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.MyDeliveriesFragment


class OrderCreatedFragment: Fragment() {
    private var _viewBinding: FragmentOrderCreatedBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOrderCreatedBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = null
            it.setDisplayHomeAsUpEnabled(false)
        }
        viewBinding.imageCheck.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeSenderFragment.newInstance())
                .commit()
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = OrderCreatedFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}