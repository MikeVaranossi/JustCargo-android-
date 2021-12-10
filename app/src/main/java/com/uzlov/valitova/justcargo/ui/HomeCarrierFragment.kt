package com.uzlov.valitova.justcargo.ui

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
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.RVHomeCarrierAdapter
import com.uzlov.valitova.justcargo.ui.fragments.RequestDetailFragment
import com.uzlov.valitova.justcargo.ui.fragments.order.OrderStepOneFragment
import javax.inject.Inject

class HomeCarrierFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

//    private val TAG: String = javaClass.simpleName
//
//    @Inject
//    lateinit var requestsUseCases: RequestsUseCases
//
//    private val listenerOnClickCargoItem = object : RVHomeCarrierAdapter.OnItemClickListener {
//        override fun click(request: Request) {
//            openFragment(RequestDetailFragment.newInstance(request))
//        }
//    }
//
//    private val requestsAdapter by lazy {
//        RVHomeCarrierAdapter(listenerOnClickCargoItem)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        requireContext().appComponent.inject(this)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        loadImage(R.drawable.image_home_fragment, viewBinding.imageViewMain)
//
//        (requireActivity() as AppCompatActivity).supportActionBar?.let {
//            it.title = getString(R.string.app_name)
//            it.setDisplayHomeAsUpEnabled(false)
//        }
//
//        viewBinding.buttonAddCargo.setOnClickListener {
//            openFragment(OrderStepOneFragment.newInstance())
//        }
//
//        showLoading()
//        loadRequests()
//    }
//
//    private fun loadRequests() {
//        requestsUseCases.getRequests().observe(this, {
//            updateUI(it)
//        })
//    }
//
//    private fun updateUI(result: List<Request>?) {
//        result?.let {
//            hideLoading()
//            setVisibilityContent(it.isEmpty())
//
//            viewBinding.rvRequests.adapter = requestsAdapter
//            requestsAdapter.setData(it)
//        }
//    }
//
//    private fun openFragment(fragment: Fragment) {
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, fragment)
//            .addToBackStack(null)
//            .commit()
//    }
//
//    private fun setVisibilityContent(isEmptyData: Boolean) {
//        with(viewBinding) {
//            if (isEmptyData) {
//                rvRequests.visibility = View.GONE
//
//                buttonAddCargo.visibility = View.VISIBLE
//                imageViewMain.visibility = View.VISIBLE
//                textViewAddCargoHint.visibility = View.VISIBLE
//            } else {
//                rvRequests.visibility = View.VISIBLE
//
//                buttonAddCargo.visibility = View.GONE
//                imageViewMain.visibility = View.GONE
//                textViewAddCargoHint.visibility = View.GONE
//            }
//        }
//    }
//
//    private fun showLoading() {
//        with(viewBinding) {
//            pbLoading.visibility = View.VISIBLE
//
//            // hide other
//            rvRequests.visibility = View.GONE
//            buttonAddCargo.visibility = View.GONE
//            imageViewMain.visibility = View.GONE
//            textViewAddCargoHint.visibility = View.GONE
//        }
//    }
//
//    private fun hideLoading() {
//        with(viewBinding) {
//            pbLoading.visibility = View.GONE
//
//            // show other
//            rvRequests.visibility = View.VISIBLE
//            buttonAddCargo.visibility = View.VISIBLE
//            imageViewMain.visibility = View.VISIBLE
//            textViewAddCargoHint.visibility = View.VISIBLE
//
//        }
//    }

    private fun loadImage(image: Int, container: ImageView) {
        view?.let {
            Glide
                .with(it.context)
                .load(image)
                .into(container)
        }
    }

    companion object {
        fun newInstance() = HomeCarrierFragment()
    }
}