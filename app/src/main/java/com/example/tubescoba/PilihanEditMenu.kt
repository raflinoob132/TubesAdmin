package com.example.tubescoba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PilihanEditMenu : AppCompatActivity() {
    private lateinit var buttonMakanan : Button
    private lateinit var buttonMinuman : Button
    private lateinit var buttonBack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pilihan_edit_menu)
        buttonMakanan=findViewById(R.id.buttonMakanan)
        buttonMinuman=findViewById(R.id.buttonMinuman)
        buttonBack=findViewById(R.id.buttonBack)

        buttonMakanan.setOnClickListener {
            val intent= Intent(this,EditMenuActivity::class.java)
            startActivity(intent)
        }

        buttonMinuman.setOnClickListener {
            val intent= Intent(this,EditMinumanActivity::class.java)
            startActivity(intent)
        }

        buttonBack.setOnClickListener {
            val intent= Intent(this,AdminActivity::class.java)
            startActivity(intent)
        }

    }
}