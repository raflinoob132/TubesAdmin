package com.example.tubescoba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PilihanRecommendMenuActivity : AppCompatActivity() {
    private lateinit var buttonMakanan: Button
    private lateinit var buttonMinuman: Button
    private lateinit var buttonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_pilihan_recommend_menu)

        buttonMakanan = findViewById(R.id.buttonMakanan)
        buttonMinuman = findViewById(R.id.buttonMinuman)
        buttonBack = findViewById(R.id.buttonBack)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        buttonMakanan.setOnClickListener {
            val intent = Intent(this, RecommendMakananActivity::class.java)
            startActivity(intent)
        }

        buttonMinuman.setOnClickListener {
            val intent = Intent(this, RecommendMinumanActivity::class.java)
            startActivity(intent)
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)

        }
    }
}