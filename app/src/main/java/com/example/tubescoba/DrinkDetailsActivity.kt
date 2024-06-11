// com/example/tubescoba/DrinkDetailsActivity.kt
package com.example.tubescoba

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class DrinkDetailsActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    private lateinit var drinkId: String
    private lateinit var drinkName: String
    private lateinit var drinkDescription: String
    private lateinit var drinkPrice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_details)

        nameTextView = findViewById(R.id.nameTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        priceTextView = findViewById(R.id.priceTextView)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)

        val intent = intent
        drinkId = intent.getStringExtra("DRINK_ID").toString()
        drinkName = intent.getStringExtra("DRINK_NAME") ?: ""
        drinkDescription = intent.getStringExtra("DRINK_DESCRIPTION") ?: ""
        drinkPrice = intent.getStringExtra("DRINK_PRICE") ?: ""

        nameTextView.text = drinkName
        descriptionTextView.text = drinkDescription
        priceTextView.text = drinkPrice

        updateButton.setOnClickListener {
            showUpdateDialog()
        }

        deleteButton.setOnClickListener {
            deleteDrink()
        }
    }

    private fun showUpdateDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_update_drink, null)
        dialogBuilder.setView(dialogView)

        val updateName = dialogView.findViewById<TextView>(R.id.updateName)
        val updateDescription = dialogView.findViewById<TextView>(R.id.updateDescription)
        val updatePrice = dialogView.findViewById<TextView>(R.id.updatePrice)

        // Pre-fill the current values
        updateName.text = drinkName
        updateDescription.text = drinkDescription
        updatePrice.text = drinkPrice

        dialogBuilder.setTitle("Update Drink")
        dialogBuilder.setPositiveButton("Update") { _, _ ->
            val newName = updateName.text.toString().trim()
            val newDescription = updateDescription.text.toString().trim()
            val newPrice = updatePrice.text.toString().trim()

            // Only update fields that are changed
            if (newName.isNotEmpty() && newName != drinkName) {
                drinkName = newName
            }
            if (newDescription.isNotEmpty() && newDescription != drinkDescription) {
                drinkDescription = newDescription
            }
            if (newPrice.isNotEmpty() && newPrice != drinkPrice) {
                drinkPrice = newPrice
            }

            updateDrink(drinkName, drinkDescription, drinkPrice)
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val b = dialogBuilder.create()
        b.show()
    }

    private fun updateDrink(name: String, description: String, price: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("minuman").child(drinkId)
        val drinkInfo = DrinkModel(drinkId, name, description, price.toDouble())
        dbRef.setValue(drinkInfo).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Drink updated", Toast.LENGTH_SHORT).show()
                // Update the TextViews with the new values
                nameTextView.text = name
                descriptionTextView.text = description
                priceTextView.text = price
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteDrink() {
        val dbRef = FirebaseDatabase.getInstance().getReference("minuman").child(drinkId)
        dbRef.removeValue().addOnCompleteListener {
            Toast.makeText(this, "Drink deleted", Toast.LENGTH_SHORT).show()
            finish() // Close this activity and go back to the previous activity
        }
    }
}
