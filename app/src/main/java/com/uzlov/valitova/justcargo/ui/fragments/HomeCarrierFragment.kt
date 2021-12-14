package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentHomeCarrierBinding
import com.uzlov.valitova.justcargo.viemodels.RequestsViewModel
import javax.inject.Inject


class HomeCarrierFragment : BaseFragment<FragmentHomeCarrierBinding>(
    FragmentHomeCarrierBinding::inflate
) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var model: RequestsViewModel
    private val adapter = RVHomeCarrierAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        model = viewModelFactory.create(RequestsViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bell_action -> Toast.makeText(
                context,
                "Здесь будут доступные уведомления",
                Toast.LENGTH_SHORT
            ).show()
            R.id.share_action -> Toast.makeText(
                context,
                "Здесь можно будет поделиться с кем-нибудь непонятно чем",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.label_home_carrier_fragment)
            it.setDisplayHomeAsUpEnabled(false)

        }
        viewBinding.recyclerViewHomeCarrier.adapter = adapter
        loadData()
        viewBinding.buttonSort.setOnClickListener {
            showPopUp(it)
        }
        viewBinding.buttonFindCargo.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment.newInstance())
                .commit()
        }

    }

    private fun loadData() {
        model.getRequests()?.observe(this, {
            checkRV(it)
        })
    }

    private fun checkRV(data: List<Request>) {
        adapter.setData(data)
        if (data.isEmpty()) {
            viewBinding.imageViewCarrierHomeEmpty.visibility = View.VISIBLE
            viewBinding.texViewLabelEmpty.visibility = View.VISIBLE
            viewBinding.recyclerViewHomeCarrier.visibility = View.GONE
            viewBinding.buttonFindCargo.visibility = View.GONE
            viewBinding.textViewFindCargo.visibility = View.GONE
            loadImage(R.drawable.image_home_carrier_fragment, viewBinding.imageViewCarrierHomeEmpty)
        } else {
            viewBinding.imageViewCarrierHomeEmpty.visibility = View.GONE
            viewBinding.texViewLabelEmpty.visibility = View.GONE
            viewBinding.recyclerViewHomeCarrier.visibility = View.VISIBLE
            viewBinding.buttonFindCargo.visibility = View.VISIBLE
            viewBinding.textViewFindCargo.text =
                getString(R.string.text_label_find_cargo, data.size)
        }
    }

    private fun showPopUp(view: View) {
        val wrapper = ContextThemeWrapper(view.context, R.style.MyStyle_PopupMenu)
        val popupMenu = context?.let { PopupMenu(wrapper, view) }
        val inflater = popupMenu?.menuInflater
        inflater?.inflate(R.menu.sort_popup_menu, popupMenu.menu)
        popupMenu?.show()
        popupMenu?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item1 -> {
                    Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show()
                }
                R.id.item2 -> {
                    Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show()
                }
                R.id.item3 -> {
                    Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show()
                }
                R.id.item4 -> {
                    Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show()
                }
            }
            true
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