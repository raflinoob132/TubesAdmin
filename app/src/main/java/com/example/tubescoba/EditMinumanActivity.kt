// com/example/tubescoba/EditMinumanActivity.kt
package com.example.tubescoba

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class EditMinumanActivity : AppCompatActivity() {

    private lateinit var drinkName: EditText
    private lateinit var drinkDescription: EditText
    private lateinit var drinkPrice: EditText
    private lateinit var buttonSelectImage: Button
    private lateinit var buttonSave: Button
    private lateinit var drinkImage: ImageView
    private var imageUri: Uri? = null

    private lateinit var dbRef: DatabaseReference
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_minuman)

        drinkName = findViewById(R.id.drinkName)
        drinkDescription = findViewById(R.id.drinkDescription)
        drinkPrice = findViewById(R.id.drinkPrice)
        buttonSelectImage = findViewById(R.id.buttonSelectImage)
        buttonSave = findViewById(R.id.buttonSave)
        drinkImage = findViewById(R.id.drinkImage)

        dbRef = FirebaseDatabase.getInstance().getReference("minuman")
        storageRef = FirebaseStorage.getInstance().reference.child("minuman_images")

        buttonSelectImage.setOnClickListener {
            selectImage()
        }

        buttonSave.setOnClickListener {
            saveDrinkData()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            drinkImage.setImageURI(imageUri)
            drinkImage.visibility = ImageView.VISIBLE
        }
    }

    private fun saveDrinkData() {
        val name = drinkName.text.toString()
        val description = drinkDescription.text.toString()
        val price = drinkPrice.text.toString().toDoubleOrNull()

        if (name.isEmpty()) {
            drinkName.error = "Please enter the drink name"
            return
        }
        if (description.isEmpty()) {
            drinkDescription.error = "Please enter the drink description"
            return
        }
        if (price == null) {
            drinkPrice.error = "Please enter a valid price"
            return
        }

        val drinkId = dbRef.push().key!!
        val drink = DrinkModel(drinkId, name, description, price, imageUri?.toString() ?: "")

        dbRef.child(drinkId).setValue(drink).addOnCompleteListener {
            Toast.makeText(this, "Drink saved successfully", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to save drink", Toast.LENGTH_SHORT).show()
        }
    }
}
