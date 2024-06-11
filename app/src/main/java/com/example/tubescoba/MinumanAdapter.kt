// com/example/tubescoba/MinumanAdapter.kt
package com.example.tubescoba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MinumanAdapter(
    private val minumanList: List<DrinkModel>,
    private val listener: MinumanAdapter.OnItemClickListener) :
    RecyclerView.Adapter<MinumanAdapter.MinumanViewHolder>() {

    inner class MinumanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nameTextView: TextView = itemView.findViewById(R.id.minumanName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.minumanDescription)
        val priceTextView: TextView = itemView.findViewById(R.id.minumanPrice)

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinumanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_minuman, parent, false)
        return MinumanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MinumanViewHolder, position: Int) {
        val currentItem = minumanList[position]
        holder.nameTextView.text = currentItem.name
        holder.descriptionTextView.text = currentItem.description
        holder.priceTextView.text = currentItem.price.toString()
    }

    override fun getItemCount(): Int {
        return minumanList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
