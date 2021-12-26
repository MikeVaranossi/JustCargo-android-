package com.uzlov.valitova.justcargo.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.data.net.Delivery
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.databinding.ItemCarrierRequestLayoutBinding


class RVUsersRequestAdapter(private var itemClickListener: OnItemClickListener? = null) :
    RecyclerView.Adapter<RVUsersRequestAdapter.RecyclerItemViewHolder>() {

    private var delivery: List<Delivery> = arrayListOf()

    private var _viewBinding: ItemCarrierRequestLayoutBinding? = null
    private val viewBinding get() = _viewBinding!!

    interface OnItemClickListener {
        fun click(request: Delivery)
        fun reject(request: Delivery)
        fun accept(request: Delivery)
    }

    fun setDelivery(data: List<Delivery>) {
        delivery = data
        notifyDataSetChanged()
    }

    // вызвать в onDestroyView()
    fun onDestroy() {
        itemClickListener = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        _viewBinding = ItemCarrierRequestLayoutBinding.inflate(inflater, parent, false)
        return RecyclerItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(
        holder: RecyclerItemViewHolder,
        position: Int,
    ) = holder.bind(delivery[position])

    override fun getItemCount(): Int = delivery.size

    inner class RecyclerItemViewHolder(private val binding: ItemCarrierRequestLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(delivery: Delivery) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(viewBinding) {
                    textView.text = delivery.trip?.carrier?.phone ?: ""
                    btnAcceptRequest.setOnClickListener {
                        itemClickListener?.accept(delivery)
                    }
                    btnRejectRequest.setOnClickListener {
                        itemClickListener?.reject(delivery)
                    }
                }
            }
        }
    }
}