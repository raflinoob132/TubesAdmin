package com.example.tubescoba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tubescoba.ViewPendingOrdersActivity

// com/example/tubescoba/AdminActivity.kt


class AdminActivity : AppCompatActivity() {

    private lateinit var buttonEditMenu: Button
    private lateinit var buttonRecommendMenu: Button
    private lateinit var buttonViewPendingOrders: Button
    private lateinit var buttonViewOrderLog: Button
    private lateinit var buttonPreviewMenu:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        buttonEditMenu = findViewById(R.id.buttonEditMenu)
        buttonRecommendMenu = findViewById(R.id.buttonRecommendMenu)
        buttonViewPendingOrders = findViewById(R.id.buttonViewPendingOrders)
        buttonViewOrderLog = findViewById(R.id.buttonViewOrderLog)
        buttonPreviewMenu=findViewById(R.id.buttonPreviewMenu)

        buttonPreviewMenu.setOnClickListener {
            startActivity(Intent(this, PilihanPreviewMenuActivity::class.java))
        }
        buttonRecommendMenu.setOnClickListener {
            startActivity(Intent(this, PilihanRecommendMenuActivity::class.java))
        }

        buttonEditMenu.setOnClickListener {
            startActivity(Intent(this, PilihanEditMenu::class.java))
        }


        buttonViewPendingOrders.setOnClickListener {
            startActivity(Intent(this, ViewPendingOrdersActivity::class.java))
        }

        buttonViewOrderLog.setOnClickListener {
            startActivity(Intent(this, ViewOrderLogActivity::class.java))
        }
    }
}
