package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.databinding.FavouritesRequestsLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailCarrierFragment
import com.uzlov.valitova.justcargo.viemodels.FavoritesRequestsViewModel
import javax.inject.Inject

class FavoritesRequestsFragment : BaseFragment<FavouritesRequestsLayoutBinding>(
    FavouritesRequestsLayoutBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var model: FavoritesRequestsViewModel

    private val listenerOnClickCargoItem = object : RVLocalRequestAdapter.OnItemClickListener<FavoriteRequestLocal> {
        override fun click(request: FavoriteRequestLocal) {
            openFragment(RequestDetailCarrierFragment.newInstance(request))

        }

        override fun addToFavorite(request: FavoriteRequestLocal) {
            model.putRequest(request)
        }

        override fun removeFromFavorite(request: FavoriteRequestLocal) {
            model.removeRequest(request)
        }
    }

    private fun openFragment(newInstance: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newInstance)
            .addToBackStack(null)
            .commit()
    }

    private val favouriteAdapter by lazy {
        RVLocalRequestAdapter(listenerOnClickCargoItem).apply {
            setVisibilityFavouritesIcon(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
        model = viewModelFactory.create(FavoritesRequestsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.favourites)
            it.setDisplayHomeAsUpEnabled(false)
        }
        showLoading()
        loadRequests()
    }

    private fun loadRequests() {
        model.getRequests().observe(viewLifecycleOwner, {
            updateUI(it)
        })

        model.getIDList().observe(viewLifecycleOwner, {
            favouriteAdapter.setIDs(it)
        })
    }

    private fun updateUI(result: List<FavoriteRequestLocal>?) {
        result?.let {
            hideLoading()
            setVisibilityContent(it.isEmpty())

            viewBinding.rvRequests.adapter = favouriteAdapter
            favouriteAdapter.setData(it)
        }
    }

    private fun setVisibilityContent(isEmptyData: Boolean) {
        with(viewBinding) {
            if (isEmptyData) {
                rvRequests.visibility = View.GONE
            } else {
                rvRequests.visibility = View.VISIBLE
            }
        }
    }

    private fun showLoading() {
        with(viewBinding) {
            pbLoading.visibility = View.VISIBLE
            // hide other
            rvRequests.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        with(viewBinding) {
            pbLoading.visibility = View.GONE
            // show other
            rvRequests.visibility = View.VISIBLE
        }
    }


    companion object {
        fun newInstance(): FavoritesRequestsFragment {
            return FavoritesRequestsFragment()
        }
    }
}