// com/example/tubescoba/PreviewDrinkActivity.kt
package com.example.tubescoba

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class PreviewMinumanActivity : AppCompatActivity(), MinumanAdapter.OnItemClickListener {

    private lateinit var recyclerViewDrink: RecyclerView
    private lateinit var MinumanAdapter: MinumanAdapter
    private lateinit var drinkList: MutableList<DrinkModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_minuman)

        recyclerViewDrink = findViewById(R.id.recyclerViewMinuman)
        recyclerViewDrink.layoutManager = LinearLayoutManager(this)
        drinkList = mutableListOf()
        MinumanAdapter = MinumanAdapter(drinkList, this)
        recyclerViewDrink.adapter = MinumanAdapter

        dbRef = FirebaseDatabase.getInstance().getReference("minuman")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                drinkList.clear()
                for (drinkSnapshot in snapshot.children) {
                    val drink = drinkSnapshot.getValue(DrinkModel::class.java)
                    drink?.let {
                        drinkList.add(it)
                    }
                }
                MinumanAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    override fun onItemClick(position: Int) {
        val clickedDrink = drinkList[position]
        val intent = Intent(this, DrinkDetailsActivity::class.java)
        intent.putExtra("DRINK_ID", clickedDrink.id)
        intent.putExtra("DRINK_NAME", clickedDrink.name)
        intent.putExtra("DRINK_DESCRIPTION", clickedDrink.description)
        intent.putExtra("DRINK_PRICE", clickedDrink.price.toString())
        startActivity(intent)
    }
}
