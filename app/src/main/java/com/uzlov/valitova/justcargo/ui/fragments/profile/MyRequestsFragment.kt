package com.uzlov.valitova.justcargo.ui.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.app.toFavoriteRequestLocal
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.MyRequestsProfileLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.RVLocalRequestAdapter
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailCarrierFragment
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailSenderFragment
import com.uzlov.valitova.justcargo.viemodels.FavoritesRequestsViewModel
import com.uzlov.valitova.justcargo.viemodels.RequestsViewModel
import javax.inject.Inject

class MyRequestsFragment : BaseFragment<MyRequestsProfileLayoutBinding>(
    MyRequestsProfileLayoutBinding::inflate) {

    private var isFromHostActivity: Boolean = true
    private lateinit var modelRequests: RequestsViewModel
    private lateinit var modelFavorites: FavoritesRequestsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var authService: AuthService


    private val listenerOnClickCargoItem =
        object : RVLocalRequestAdapter.OnItemClickListener<Request> {
            override fun click(request: Request) {
                openFragment(RequestDetailSenderFragment.newInstance(request))

            }

            override fun addToFavorite(request: Request) {
                modelFavorites.putRequest(request.toFavoriteRequestLocal())
            }

            override fun removeFromFavorite(request: Request) {
                modelFavorites.removeRequest(request.toFavoriteRequestLocal())
            }
        }

    private val requestsAdapter by lazy {
        RVLocalRequestAdapter(listenerOnClickCargoItem).apply {
            setVisibilityFavouritesIcon(false)
        }
    }

    companion object {
        fun newInstance(isFromHostActivity: Boolean): MyRequestsFragment {
            return MyRequestsFragment().apply {
                arguments = bundleOf(Constant.KEY_FROM_HOST_ACTIVITY to isFromHostActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        modelRequests = viewModelFactory.create(RequestsViewModel::class.java)
        modelFavorites = viewModelFactory.create(FavoritesRequestsViewModel::class.java)

        arguments?.let {
            isFromHostActivity = it.getBoolean(Constant.KEY_FROM_HOST_ACTIVITY)
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.my_packages)
            it.setDisplayHomeAsUpEnabled(!isFromHostActivity)
        }
        initListeners()
        viewBinding.rvRequests.adapter = requestsAdapter

        showLoading()
        loadRequests()
    }

    private fun initListeners() {
        viewBinding.refreshDataLayout.setOnRefreshListener {
            viewBinding.refreshDataLayout.isRefreshing = true
            loadRequests()
        }
    }

    // загружаем только свои заявки
    private fun loadRequests() {
        authService.currentUser()?.let { user ->
            modelRequests.getRequestsWithPhone(user.phone ?: "")?.observe(this, {
                updateUI(it)
            })
        }

    }

    private fun updateUI(result: List<Request>?) {
        result?.let {
            hideLoading()
            setVisibilityContent(it.isEmpty())


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