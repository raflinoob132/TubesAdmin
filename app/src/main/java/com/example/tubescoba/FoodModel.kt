// com/example/tubescoba/FoodModel.kt
package com.example.tubescoba

data class FoodModel(
    val id: String? = "",
    val name: String? = "",
    val description: String? = "",
    val price: Double? = 0.0,
    val imageUrl: String? = "",
    val recommended: Boolean = false // tambahkan properti recommended

)
