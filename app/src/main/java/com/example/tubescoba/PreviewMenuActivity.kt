// com/example/tubescoba/PreviewMenuActivity.kt
package com.example.tubescoba

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class PreviewMenuActivity : AppCompatActivity(), MenuAdapter.OnItemClickListener {

    private lateinit var recyclerViewMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var menuList: MutableList<FoodModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_menu)

        recyclerViewMenu = findViewById(R.id.recyclerViewMenu)
        recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        menuList = mutableListOf()
        menuAdapter = MenuAdapter(menuList, this)
        recyclerViewMenu.adapter = menuAdapter

        dbRef = FirebaseDatabase.getInstance().getReference("makanan")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuList.clear()
                for (menuSnapshot in snapshot.children) {
                    val menu = menuSnapshot.getValue(FoodModel::class.java)
                    menu?.let {
                        menuList.add(it)
                    }
                }
                menuAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    override fun onItemClick(position: Int) {
        val clickedMenu = menuList[position]
        val intent = Intent(this, FoodDetailsActivity::class.java)
        intent.putExtra("FOOD_ID", clickedMenu.id)
        intent.putExtra("FOOD_NAME", clickedMenu.name)
        intent.putExtra("FOOD_DESCRIPTION", clickedMenu.description)
        intent.putExtra("FOOD_PRICE", clickedMenu.price.toString())
        startActivity(intent)
    }
}
