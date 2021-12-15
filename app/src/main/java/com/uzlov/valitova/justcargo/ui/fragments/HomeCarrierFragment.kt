package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentHomeCarrierBinding


class HomeCarrierFragment : Fragment() {
    private var _viewBinding: FragmentHomeCarrierBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val adapter = RVHomeCarrierAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeCarrierBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.app_name)
            it.setDisplayHomeAsUpEnabled(false)

        }
        viewBinding.recyclerViewHomeCarrier.adapter = adapter
        viewBinding.buttonFindCargo.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment.newInstance())
                .commit()
        }
        checkRV()

    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun checkRV() {
        if (adapter.itemCount == 0) {
            viewBinding.imageViewCarrierHomeEmpty.visibility = View.VISIBLE
            viewBinding.texViewLabelEmpty.visibility = View.VISIBLE
            viewBinding.recyclerViewHomeCarrier.visibility = View.GONE
            viewBinding.buttonFindCargo.visibility = View.GONE
            loadImage(R.drawable.image_home_carrier_fragment, viewBinding.imageViewCarrierHomeEmpty)
        } else {
            viewBinding.imageViewCarrierHomeEmpty.visibility = View.GONE
            viewBinding.texViewLabelEmpty.visibility = View.GONE
            viewBinding.recyclerViewHomeCarrier.visibility = View.VISIBLE
            viewBinding.buttonFindCargo.visibility = View.VISIBLE
        }
    }

    private fun loadImage(image: Int, container: ImageView) {
        view?.let {
            Glide
                .with(it.context)
                .load(image)
                .into(container)
        }
    }

    companion object {
        fun newInstance() =
            HomeCarrierFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}