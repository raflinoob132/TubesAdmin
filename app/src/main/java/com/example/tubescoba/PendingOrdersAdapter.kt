package com.example.tubescoba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tubescoba.OrderModel
import com.example.tubescoba.R

class PendingOrdersAdapter(private val ordersList: List<OrderModel>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<PendingOrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val customerNameTextView: TextView = itemView.findViewById(R.id.customerName)
        val totalPriceTextView: TextView = itemView.findViewById(R.id.totalPrice)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pending_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = ordersList[position]
        holder.customerNameTextView.text = currentItem.customerName
        holder.totalPriceTextView.text = "Total Price: ${currentItem.totalPrice}"
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }
}