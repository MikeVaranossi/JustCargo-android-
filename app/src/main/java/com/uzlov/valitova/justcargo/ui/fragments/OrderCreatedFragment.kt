package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.databinding.FragmentOrderCreatedBinding


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