package com.uzlov.valitova.justcargo.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.ItemCardCargoBinding
import java.text.SimpleDateFormat
import java.util.*

class RVHomeCarrierAdapter(private var itemClickListener: OnItemClickListener? = null) :
    RecyclerView.Adapter<RVHomeCarrierAdapter.RecyclerItemViewHolder>() {

    private var requests: List<Request> = arrayListOf()
    private var idList: List<Long> = arrayListOf()

    private var _viewBinding: ItemCardCargoBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val simpleFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    interface OnItemClickListener {
        fun click(request: Request)
        fun addToFavorite(request: Request)
        fun removeFromFavorite(request: Request)
    }

    fun setData(data: List<Request>) {
        requests = data
        notifyDataSetChanged()
    }

    fun setIDs(_idList: List<Long>) {
        idList = _idList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        _viewBinding = ItemCardCargoBinding.inflate(inflater, parent, false)
        return RecyclerItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(requests[position])
    }

    override fun getItemCount(): Int {
        return requests.size
    }

    inner class RecyclerItemViewHolder(private val binding: ItemCardCargoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Request) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(viewBinding) {
                    textViewNameCargo.text = data.getShortInfoItem()
                    textViewDate.text = simpleFormat.format(data.getRequestTimeItem()).toString()
                    textViewCost.text = "${data.getCostItem()} ₽"
                    textViewFromTo.text =
                        " ${data.getDepartureItem()}  -  ${data.getDestinationItem()} "
                    textViewToDetails.setOnClickListener {
                        itemClickListener?.click(data)
                    }
                    checkboxFavourite.isChecked = idList.contains(data.id)
                    checkboxFavourite.setOnClickListener {

                        if (checkboxFavourite.isChecked) {
                            Snackbar.make(
                                binding.root,
                                binding.root.context.getString(R.string.cargo_is_added_into_cart),
                                Snackbar.LENGTH_LONG
                            )
                                .setBackgroundTint(ContextCompat.getColor(binding.root.context,
                                    R.color.dark_primary_color))
                                .show()
                            itemClickListener?.addToFavorite(data)
                        } else {
                            itemClickListener?.removeFromFavorite(data)
                        }
                    }
                }
            }
        }
    }
}