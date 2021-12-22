package com.uzlov.valitova.justcargo.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.ItemCardCargoBinding
import java.text.SimpleDateFormat
import java.util.*

class RVLocalRequestAdapter<T : IViewItemAdapter>(private var itemClickListener: OnItemClickListener<T>? = null) :
    RecyclerView.Adapter<RVLocalRequestAdapter<T>.RecyclerItemViewHolder>() {

    private var requests: List<T> = arrayListOf()
    private var idList: List<Long> = arrayListOf()

    private var _viewBinding: ItemCardCargoBinding? = null
    private val viewBinding get() = _viewBinding!!

    private var isEnableFavouriteIcon: Boolean = true

    private val simpleFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    interface OnItemClickListener<T> {
        fun click(request: T)
        fun addToFavorite(request: T)
        fun removeFromFavorite(request: T)
    }

    fun setData(data: List<T>) {
        this.requests = data
        notifyDataSetChanged()
    }

    fun setVisibilityFavouritesIcon(enable: Boolean) {
        isEnableFavouriteIcon = enable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVLocalRequestAdapter<T>.RecyclerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        _viewBinding = ItemCardCargoBinding.inflate(inflater, parent, false)
        return RecyclerItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RVLocalRequestAdapter<T>.RecyclerItemViewHolder, position: Int) {
        if (isEnableFavouriteIcon){
            holder.bind(requests[position])
        } else {
            holder.bindWithFavouritesIcon(requests[position])
        }
    }

    override fun getItemCount(): Int = requests.size

    inner class RecyclerItemViewHolder(private val binding: ItemCardCargoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: T) {
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
                    checkboxFavourite.isChecked = idList.contains(data.getIdRequest())
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

        fun bindWithFavouritesIcon(request: T) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(viewBinding) {
                    textViewNameCargo.text = request.getShortInfoItem()
                    textViewDate.text = simpleFormat.format(request.getRequestTimeItem()).toString()
                    textViewCost.text = "${request.getCostItem()} ₽"
                    textViewFromTo.text =
                        " ${request.getDepartureItem()}  -  ${request.getDestinationItem()} "
                    textViewToDetails.setOnClickListener {
                        itemClickListener?.click(request)
                    }
                    checkboxFavourite.visibility = View.GONE
                }
            }
        }
    }
}