package com.example.tubescoba

data class OrderItemModel(
    val id: String? = null,
    val description: String? = null,
    val name: String? = null,
    val price: Double? = null,
    val quantity: Int = 1
)