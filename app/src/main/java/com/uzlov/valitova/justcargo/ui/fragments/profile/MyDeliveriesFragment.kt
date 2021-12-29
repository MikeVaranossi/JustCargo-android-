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
import com.uzlov.valitova.justcargo.data.local.MyRequestLocal
import com.uzlov.valitova.justcargo.databinding.MyDeliveriesProfileLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.RVLocalRequestAdapter
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailCarrierFragment
import com.uzlov.valitova.justcargo.viemodels.DeliveryViewModel
import com.uzlov.valitova.justcargo.viemodels.FavoritesRequestsViewModel
import com.uzlov.valitova.justcargo.viemodels.MyRequestsViewModel
import javax.inject.Inject

class MyDeliveriesFragment : BaseFragment<MyDeliveriesProfileLayoutBinding>(
    MyDeliveriesProfileLayoutBinding::inflate) {

    private var isFromHostActivity = true
    @Inject
    lateinit var authService: AuthService
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var model: DeliveryViewModel
    lateinit var favouritesVModel: FavoritesRequestsViewModel
    lateinit var myRequestModel: MyRequestsViewModel

    private val callback = object : RVLocalRequestAdapter.OnItemClickListener<MyRequestLocal>{
        override fun click(request: MyRequestLocal) {
            openFragment(RequestDetailCarrierFragment.newInstance(request.toFavoriteRequestLocal()))
        }

        override fun addToFavorite(request: MyRequestLocal) {
            favouritesVModel.putRequest(request.toFavoriteRequestLocal())
        }

        override fun removeFromFavorite(request: MyRequestLocal) {
            favouritesVModel.removeRequest(request.toFavoriteRequestLocal())
        }
    }

    private val adapter by lazy {
        RVLocalRequestAdapter(callback).apply {
            setVisibilityFavouritesIcon(true)
        }
    }

    companion object {
        fun newInstance(isFromHostActivity: Boolean = true): MyDeliveriesFragment {
            return MyDeliveriesFragment().apply {
                arguments = bundleOf(Constant.KEY_FROM_HOST_ACTIVITY to isFromHostActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        model = viewModelFactory.create(DeliveryViewModel::class.java)
        favouritesVModel = viewModelFactory.create(FavoritesRequestsViewModel::class.java)
        myRequestModel = viewModelFactory.create(MyRequestsViewModel::class.java)

        arguments?.let {
            isFromHostActivity = it.getBoolean(Constant.KEY_FROM_HOST_ACTIVITY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.my_deliveries)
            it.setDisplayHomeAsUpEnabled(!isFromHostActivity)
        }
        viewBinding.rvDeliveries.adapter = adapter
        initListeners()
        loadData()
    }

    private fun initListeners() {
        viewBinding.refreshDataLayout.setOnRefreshListener {
            viewBinding.refreshDataLayout.isRefreshing = true
            loadData()
        }
    }

    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun loadData() {
        authService.currentUser()?.phone?.let { phone ->
            // загружаем тех кто в "Избранное"
            favouritesVModel.getIDList().observe(viewLifecycleOwner, {
                adapter.setIDs(it)
            })
            model.getDeliveriesWithCarrierPhone(phone).observe(this, {
                val list = it.map { delivery ->
                    delivery.request!!
                }
                myRequestModel.putRequest(list)
            })

            myRequestModel.getMyRequests()?.observe(this, {
                adapter.setData(it)
                updateUI(it)
            })
        }
    }

    private fun updateUI(data: List<MyRequestLocal>) {
        if (data.isEmpty()) {
            viewBinding.tvNotAvailableDeliveries.visibility = View.VISIBLE
            viewBinding.rvDeliveries.visibility = View.GONE
        } else {
            viewBinding.tvNotAvailableDeliveries.visibility = View.GONE
            viewBinding.rvDeliveries.visibility = View.VISIBLE
        }
        viewBinding.refreshDataLayout.isRefreshing = false
    }
}