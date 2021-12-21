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
import java.text.SimpleDateFormat
import java.util.*

class RVHomeCarrierAdapter(private var itemClickListener: OnItemClickListener? = null) :
    RecyclerView.Adapter<RVHomeCarrierAdapter.RecyclerItemViewHolder>() {

    private var data: List<Request> = arrayListOf()
    private var idList: List<Long> = arrayListOf()

    interface OnItemClickListener {
        fun click(request: Request)
        fun addToFavorite(request: Request)
        fun removeFromFavorite(request: Request)
    }

    fun setData(data: List<Request>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setIDs(idList: List<Long>) {
        this.idList = idList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card_cargo, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Request) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.apply {
                    findViewById<TextView>(R.id.text_view_name_cargo).text =
                        data.shortInfo
                    val simpleFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                    findViewById<TextView>(R.id.text_view_date).text =
                        simpleFormat.format(data.deliveryTime).toString()
                    findViewById<TextView>(R.id.text_view_cost).text =
                        "${data.cost} ₽"
                    findViewById<TextView>(R.id.text_view_from_to).text =
                        data.departure + " - " + data.destination
                    findViewById<TextView>(R.id.text_view_to_details).setOnClickListener {
                        itemClickListener?.click(data)
                    }
                    findViewById<CheckBox>(R.id.checkbox_favourite).isChecked = idList.contains(data.id)
                    findViewById<CheckBox>(R.id.checkbox_favourite).setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            Snackbar.make(
                                itemView,
                                context.getString(R.string.cargo_is_added_into_cart),
                                Snackbar.LENGTH_LONG
                            )
                                .setBackgroundTint(ContextCompat.getColor(context,
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