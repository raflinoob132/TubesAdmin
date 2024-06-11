package com.example.tubescoba

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ViewOrderLogActivity : AppCompatActivity() {

    private lateinit var recyclerViewOrderLogs: RecyclerView
    private lateinit var orderLogAdapter: OrderLogAdapter
    private lateinit var orderLogList: MutableList<OrderLogModel>
    private lateinit var dbRefOrderLogs: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_order_log)

        recyclerViewOrderLogs = findViewById(R.id.recyclerViewOrderLogs)
        recyclerViewOrderLogs.layoutManager = LinearLayoutManager(this)

        orderLogList = mutableListOf()
        orderLogAdapter = OrderLogAdapter(orderLogList)
        recyclerViewOrderLogs.adapter = orderLogAdapter

        dbRefOrderLogs = FirebaseDatabase.getInstance().getReference("order_logs")

        loadOrderLogs()
    }

    private fun loadOrderLogs() {
        dbRefOrderLogs.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderLogList.clear()
                for (orderLogSnapshot in snapshot.children) {
                    val orderLog = orderLogSnapshot.getValue(OrderLogModel::class.java)
                    orderLog?.let { orderLogList.add(it) }
                }
                orderLogAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewOrderLogActivity, "Failed to load order logs: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


