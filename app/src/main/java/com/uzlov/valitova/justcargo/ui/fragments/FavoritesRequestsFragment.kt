package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.databinding.FavouritesRequestsLayoutBinding
import com.uzlov.valitova.justcargo.viemodels.FavoritesRequestsViewModel
import javax.inject.Inject

class FavoritesRequestsFragment : BaseFragment<FavouritesRequestsLayoutBinding>(
    FavouritesRequestsLayoutBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var model: FavoritesRequestsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
        model = viewModelFactory.create(FavoritesRequestsViewModel::class.java)
    }

    companion object {
        fun newInstance(): FavoritesRequestsFragment {
            return FavoritesRequestsFragment()
        }
    }
}