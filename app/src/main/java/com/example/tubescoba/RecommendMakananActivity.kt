// com/example/tubescoba/RecommendMakananActivity.kt
package com.example.tubescoba

import RekomendasiMenuMakananAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

// RecommendMakananActivity.kt
class RecommendMakananActivity : AppCompatActivity(), RekomendasiMenuMakananAdapter.OnItemClickListener {

    private lateinit var recyclerViewMakanan: RecyclerView
    private lateinit var makananAdapter: RekomendasiMenuMakananAdapter
    private lateinit var makananList: MutableList<FoodModel>

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_makanan)

        recyclerViewMakanan = findViewById(R.id.recyclerViewMakanan)
        recyclerViewMakanan.layoutManager = LinearLayoutManager(this)
        makananList = mutableListOf()
        makananAdapter = RekomendasiMenuMakananAdapter(makananList, this)
        recyclerViewMakanan.adapter = makananAdapter

        dbRef = FirebaseDatabase.getInstance().getReference("makanan")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                makananList.clear()
                for (makananSnapshot in snapshot.children) {
                    val makanan = makananSnapshot.getValue(FoodModel::class.java)
                    makanan?.let {
                        makananList.add(it)
                    }
                }
                makananAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    override fun onItemClick(position: Int) {
        val selectedItem = makananList[position]
        val rekomendasi = selectedItem.recommended
        selectedItem.id?.let { id ->
            dbRef.child(id).child("recommended").setValue(!rekomendasi)
        }
    }
}

