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

class RVLocalRequestAdapter<T : IViewItemAdapter>(private var itemClickListener: OnItemClickListener<T>? = null) :
    RecyclerView.Adapter<RVLocalRequestAdapter<T>.RecyclerItemViewHolder>() {

    private var data: List<T> = arrayListOf()

    interface OnItemClickListener<T> {
        fun click(request: T)
        fun addToFavorite(request: T)
        fun removeFromFavorite(request: T)
    }

    fun setData(data: List<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card_cargo, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvNameCargo = view.findViewById<TextView>(R.id.text_view_name_cargo)
        private val tvRequestDate = view.findViewById<TextView>(R.id.text_view_date)
        private val tvCostCargo = view.findViewById<TextView>(R.id.text_view_cost)
        private val tvTrackCargo = view.findViewById<TextView>(R.id.text_view_from_to)
        private val tvDetailCargo = view.findViewById<TextView>(R.id.text_view_to_details)
        private val cbFavouriteCargo = view.findViewById<CheckBox>(R.id.checkbox_favourite)

        fun bind(item: T) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.apply {
                    tvNameCargo.text = item.getShortInfoItem()
                    tvRequestDate.text = item.getRequestTimeItem().toString()
                    tvCostCargo.text = "${item.getCostItem()} ₽"
                    tvTrackCargo.text =  " ${item.getDepartureItem()}  -  ${item.getDestinationItem()} "
                    cbFavouriteCargo.isChecked = item.getIsFavourites()!!

                    tvDetailCargo.setOnClickListener {
                        itemClickListener?.click(item)
                    }
                    cbFavouriteCargo.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            Snackbar.make(
                                itemView,
                                context.getString(R.string.cargo_is_added_into_cart),
                                Snackbar.LENGTH_LONG
                            )
                                .setBackgroundTint(ContextCompat.getColor(context,
                                    R.color.dark_primary_color))
                                .show()
                            itemClickListener?.addToFavorite(item)
                        } else {
                            itemClickListener?.removeFromFavorite(item)
                        }
                    }
                }
            }
        }
    }
}