package com.example.tubescoba

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tubescoba.OrderLogModel
import com.example.tubescoba.R

class OrderLogAdapter(private val orderLogs: List<OrderLogModel>) :
    RecyclerView.Adapter<OrderLogAdapter.OrderLogViewHolder>() {

    inner class OrderLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textOrderId: TextView = itemView.findViewById(R.id.textOrderId)
        val textCustomerName: TextView = itemView.findViewById(R.id.textCustomerName)
        val textTotalPrice: TextView = itemView.findViewById(R.id.textTotalPrice)
        val textOrderDate: TextView = itemView.findViewById(R.id.textOrderDate)
        val textOrderItems: TextView = itemView.findViewById(R.id.textOrderItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderLogViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_log, parent, false)
        return OrderLogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderLogViewHolder, position: Int) {
        val currentOrder = orderLogs[position]
        holder.textOrderId.text = "Order ID: ${currentOrder.id}"
        holder.textCustomerName.text = "Customer: ${currentOrder.customerName}"
        holder.textTotalPrice.text = "Total Price: $${currentOrder.totalPrice}"
        holder.textOrderDate.text = "Order Date: ${currentOrder.orderDate}"

        // Set order items
        val orderItemsDetail = StringBuilder()
        currentOrder.items?.forEach { (_, item) ->
            orderItemsDetail.append("${item?.name} x ${item?.quantity}: ${item?.price?.times(item.quantity ?: 0)}\n")
        }
        holder.textOrderItems.text = orderItemsDetail.toString()
    }

    override fun getItemCount(): Int {
        return orderLogs.size
    }
}


