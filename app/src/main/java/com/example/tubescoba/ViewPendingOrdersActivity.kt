package com.example.tubescoba

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tubescoba.OrderModel
import com.example.tubescoba.PendingOrdersAdapter
import com.example.tubescoba.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class ViewPendingOrdersActivity : AppCompatActivity(), PendingOrdersAdapter.OnItemClickListener {

    private lateinit var recyclerViewPendingOrders: RecyclerView
    private lateinit var ordersAdapter: PendingOrdersAdapter
    private lateinit var ordersList: MutableList<OrderModel>
    private lateinit var dbRefPending: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pending_orders)

        recyclerViewPendingOrders = findViewById(R.id.recyclerViewPendingOrders)
        recyclerViewPendingOrders.layoutManager = LinearLayoutManager(this)

        ordersList = mutableListOf()
        ordersAdapter = PendingOrdersAdapter(ordersList, this)
        recyclerViewPendingOrders.adapter = ordersAdapter

        dbRefPending = FirebaseDatabase.getInstance().getReference("pending_orders")

        loadPendingOrders()
    }

    private fun loadPendingOrders() {
        dbRefPending.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ordersList.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(OrderModel::class.java)
                    order?.let { ordersList.add(it) }
                }
                ordersAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewPendingOrdersActivity, "Failed to load orders: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(position: Int) {
        val selectedOrder = ordersList[position]
        showOrderDetailDialog(selectedOrder)
    }

    private fun showOrderDetailDialog(order: OrderModel) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_order_detail, null)
        val textCustomerName: TextView = dialogView.findViewById(R.id.textCustomerName)
        val textOrderItems: TextView = dialogView.findViewById(R.id.textOrderItems)
        val textTotalPrice: TextView = dialogView.findViewById(R.id.textTotalPrice)
        val buttonCompleteOrder: Button = dialogView.findViewById(R.id.buttonCompleteOrder)

        // Set customer name
        textCustomerName.text = "Customer: ${order.customerName}"

        // Set total price
        textTotalPrice.text = "Total Price: $${order.totalPrice}"

        // Load and display order items detail
        loadOrderItems(order.items, textOrderItems)

        // Create and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Add action for the Complete Order button
        buttonCompleteOrder.setOnClickListener {
            completeOrder(order) // Panggil fungsi completeOrder
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }

    private fun loadOrderItems(orderItems: Map<String, OrderItemModel>?, textView: TextView) {
        orderItems?.let { items ->
            val orderItemsDetail = StringBuilder()
            items.forEach { (_, item) ->
                orderItemsDetail.append("${item?.name} x ${item?.quantity}: ${item?.price?.times(item.quantity) ?: 0}\n")
            }
            textView.text = orderItemsDetail.toString()
        }
    }

    // Fungsi untuk menyelesaikan pesanan
    private fun completeOrder(order: OrderModel) {
        val orderId = order.id ?: return
        val logRef = FirebaseDatabase.getInstance().getReference("order_logs").child(orderId)

        // Simpan pesanan ke log pesanan
        logRef.setValue(order)
            .addOnSuccessListener {
                // Tambahkan tanggal pesanan
                logRef.child("orderDate").setValue(getCurrentDate())

                // Hapus pesanan dari pending_orders
                dbRefPending.child(orderId).removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Order completed successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to complete order: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to complete order: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Fungsi untuk mendapatkan tanggal saat ini dalam format yyyy-MM-dd
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
