package com.uzlov.valitova.justcargo.ui.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.app.toFavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.MyRequestsProfileLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.RVHomeCarrierAdapter
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailCarrierFragment
import com.uzlov.valitova.justcargo.viemodels.FavoritesRequestsViewModel
import com.uzlov.valitova.justcargo.viemodels.RequestsViewModel
import javax.inject.Inject

class MyRequestsFragment : BaseFragment<MyRequestsProfileLayoutBinding>(
    MyRequestsProfileLayoutBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var modelRequests: RequestsViewModel
    lateinit var modelFavorites: FavoritesRequestsViewModel


    private val listenerOnClickCargoItem = object : RVHomeCarrierAdapter.OnItemClickListener {
        override fun click(request: Request) {
            openFragment(RequestDetailCarrierFragment.newInstance(request))
            
        }

        override fun addToFavorite(request: Request) {
            modelFavorites.putRequest(request.toFavoriteRequestLocal())
        }

        override fun removeFromFavorite(request: Request) {
            modelFavorites.removeRequest(request.toFavoriteRequestLocal())
        }
    }

    private val requestsAdapter by lazy {
        RVHomeCarrierAdapter(listenerOnClickCargoItem)
    }

    companion object {
        fun newInstance(): MyRequestsFragment {
            return MyRequestsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        modelRequests = viewModelFactory.create(RequestsViewModel::class.java)
        modelFavorites = viewModelFactory.create(FavoritesRequestsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.my_packages)
            it.setDisplayHomeAsUpEnabled(true)
        }
        initListeners()
        showLoading()
        loadRequests()
    }

    private fun initListeners() {
        viewBinding.refreshDataLayout.setOnRefreshListener {
            viewBinding.refreshDataLayout.isRefreshing = true
            loadRequests()
        }
    }

    private fun loadRequests() {
        modelRequests.getRequests()?.observe(this, {
            updateUI(it)
        })
    }

    private fun updateUI(result: List<Request>?) {
        result?.let {
            hideLoading()
            setVisibilityContent(it.isEmpty())

            viewBinding.rvRequests.adapter = requestsAdapter
            requestsAdapter.setData(it)
        }
    }

    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
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
}