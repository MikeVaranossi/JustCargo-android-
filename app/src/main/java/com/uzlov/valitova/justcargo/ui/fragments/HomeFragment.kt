package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentHomeBinding
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import com.uzlov.valitova.justcargo.ui.fragments.order.OrderStepOneFragment
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage(R.drawable.image_home_fragment, viewBinding.imageViewMain)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.app_name)
            it.setDisplayHomeAsUpEnabled(false)
        }

        viewBinding.buttonAddCargo.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrderStepOneFragment.newInstance())
                .addToBackStack(null)
                .commit()

        }

    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    private fun loadImage(image: Int, container: ImageView) {
        view?.let {
            Glide
                .with(it.context)
                .load(image)
                .into(container)
        }
    }
}