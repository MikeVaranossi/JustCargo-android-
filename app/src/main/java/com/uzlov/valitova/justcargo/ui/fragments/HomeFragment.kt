package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentHomeBinding
import com.uzlov.valitova.justcargo.ui.fragments.order.OrderStepOneFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        menu.setGroupVisible(R.id.group2, false)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.bell_action -> Toast.makeText(context, "Здесь будут доступные уведомления", Toast.LENGTH_SHORT).show()
        }
        return true
    }


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