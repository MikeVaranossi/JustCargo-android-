package com.uzlov.valitova.justcargo.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.databinding.ItemCarrierRequestLayoutBinding


class RVUsersRequestAdapter(private var itemClickListener: OnItemClickListener? = null) :
    RecyclerView.Adapter<RVUsersRequestAdapter.RecyclerItemViewHolder>() {

    private var users: List<User> = arrayListOf()

    private var _viewBinding: ItemCarrierRequestLayoutBinding? = null
    private val viewBinding get() = _viewBinding!!

    interface OnItemClickListener {
        fun click(request: User)
        fun reject(request: User)
        fun accept(request: User)
    }

    fun setUsers(data: List<User>) {
        this.users = data
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
    ) = holder.bind(users[position])

    override fun getItemCount(): Int = users.size

    inner class RecyclerItemViewHolder(private val binding: ItemCarrierRequestLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(viewBinding) {

                }
            }
        }
    }
}