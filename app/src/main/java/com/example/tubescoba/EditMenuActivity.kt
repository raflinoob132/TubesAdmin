// com/example/tubescoba/EditMenuActivity.kt
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

class EditMenuActivity : AppCompatActivity() {

    private lateinit var foodName: EditText
    private lateinit var foodDescription: EditText
    private lateinit var foodPrice: EditText
    private lateinit var buttonSelectImage: Button
    private lateinit var buttonSave: Button
    private lateinit var foodImage: ImageView
    private var imageUri: Uri? = null

    private lateinit var dbRef: DatabaseReference
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)

        foodName = findViewById(R.id.foodName)
        foodDescription = findViewById(R.id.foodDescription)
        foodPrice = findViewById(R.id.foodPrice)
        buttonSelectImage = findViewById(R.id.buttonSelectImage)
        buttonSave = findViewById(R.id.buttonSave)
        foodImage = findViewById(R.id.foodImage)

        dbRef = FirebaseDatabase.getInstance().getReference("makanan")
        storageRef = FirebaseStorage.getInstance().reference.child("makanan_images")

        buttonSelectImage.setOnClickListener {
            selectImage()
        }

        buttonSave.setOnClickListener {
            saveMenuData()
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
            foodImage.setImageURI(imageUri)
            foodImage.visibility = ImageView.VISIBLE
        }
    }

    private fun saveMenuData() {
        val name = foodName.text.toString()
        val description = foodDescription.text.toString()
        val price = foodPrice.text.toString().toDoubleOrNull()

        if (name.isEmpty()) {
            foodName.error = "Please enter the food name"
            return
        }
        if (description.isEmpty()) {
            foodDescription.error = "Please enter the food description"
            return
        }
        if (price == null) {
            foodPrice.error = "Please enter a valid price"
            return
        }

        val menuId = dbRef.push().key!!
        val food = FoodModel(menuId, name, description, price, imageUri?.toString() ?: "")

        dbRef.child(menuId).setValue(food).addOnCompleteListener {
            Toast.makeText(this, "Menu saved successfully", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to save menu", Toast.LENGTH_SHORT).show()
        }
    }
}
