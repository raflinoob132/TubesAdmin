// MakananAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tubescoba.FoodModel
import com.example.tubescoba.R

class RekomendasiMenuMakananAdapter(private val makananList: List<FoodModel>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RekomendasiMenuMakananAdapter.MakananViewHolder>() {

    inner class MakananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nameTextView: TextView = itemView.findViewById(R.id.makananName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.makananDescription)
        val priceTextView: TextView = itemView.findViewById(R.id.makananPrice)
        val recommendButton: Button = itemView.findViewById(R.id.buttonRecommend)

        init {
            itemView.setOnClickListener(this)
            recommendButton.setOnClickListener(this)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rekomendasi_makanan, parent, false)
        return MakananViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val currentItem = makananList[position]
        holder.nameTextView.text = currentItem.name
        holder.descriptionTextView.text = currentItem.description
        holder.priceTextView.text = currentItem.price.toString()
        // Atur teks tombol berdasarkan status rekomendasi
        if (currentItem.recommended) {
            holder.recommendButton.text = "Unrecommend"
        } else {
            holder.recommendButton.text = "Recommend"
        }
    }

    override fun getItemCount(): Int {
        return makananList.size
    }
}
