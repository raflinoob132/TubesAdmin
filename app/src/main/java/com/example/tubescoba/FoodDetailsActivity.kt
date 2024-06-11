// com/example/tubescoba/FoodDetailsActivity.kt
package com.example.tubescoba

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class FoodDetailsActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    private lateinit var foodId: String
    private lateinit var foodName: String
    private lateinit var foodDescription: String
    private lateinit var foodPrice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        nameTextView = findViewById(R.id.nameTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        priceTextView = findViewById(R.id.priceTextView)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)

        val intent = intent
        foodId = intent.getStringExtra("FOOD_ID").toString()
        foodName = intent.getStringExtra("FOOD_NAME") ?: ""
        foodDescription = intent.getStringExtra("FOOD_DESCRIPTION") ?: ""
        foodPrice = intent.getStringExtra("FOOD_PRICE") ?: ""

        nameTextView.text = foodName
        descriptionTextView.text = foodDescription
        priceTextView.text = foodPrice

        updateButton.setOnClickListener {
            showUpdateDialog()
        }

        deleteButton.setOnClickListener {
            deleteFood()
        }
    }

    private fun showUpdateDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_update_food, null)
        dialogBuilder.setView(dialogView)

        val updateName = dialogView.findViewById<TextView>(R.id.updateName)
        val updateDescription = dialogView.findViewById<TextView>(R.id.updateDescription)
        val updatePrice = dialogView.findViewById<TextView>(R.id.updatePrice)

        // Pre-fill the current values
        updateName.text = foodName
        updateDescription.text = foodDescription
        updatePrice.text = foodPrice

        dialogBuilder.setTitle("Update Food")
        dialogBuilder.setPositiveButton("Update") { _, _ ->
            val newName = updateName.text.toString().trim()
            val newDescription = updateDescription.text.toString().trim()
            val newPrice = updatePrice.text.toString().trim()

            // Only update fields that are changed
            if (newName.isNotEmpty() && newName != foodName) {
                foodName = newName
            }
            if (newDescription.isNotEmpty() && newDescription != foodDescription) {
                foodDescription = newDescription
            }
            if (newPrice.isNotEmpty() && newPrice != foodPrice) {
                foodPrice = newPrice
            }

            updateFood(foodName, foodDescription, foodPrice)
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val b = dialogBuilder.create()
        b.show()
    }

    private fun updateFood(name: String, description: String, price: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("makanan").child(foodId)
        val foodInfo = FoodModel(foodId, name, description, price.toDouble())
        dbRef.setValue(foodInfo).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Food updated", Toast.LENGTH_SHORT).show()
                nameTextView.text = name
                descriptionTextView.text = description
                priceTextView.text = price
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteFood() {
        val dbRef = FirebaseDatabase.getInstance().getReference("makanan").child(foodId)
        dbRef.removeValue().addOnCompleteListener {
            Toast.makeText(this, "Food deleted", Toast.LENGTH_SHORT).show()
            finish() // Close this activity and go back to PreviewMenuActivity
        }
    }
}
