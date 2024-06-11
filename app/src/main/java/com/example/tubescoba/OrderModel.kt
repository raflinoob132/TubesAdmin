package com.example.tubescoba


data class OrderModel(
    val id: String? = null,
    val customerName: String? = null,
    val items: Map<String, OrderItemModel>? = null, // Add orderItems field
    val totalPrice: Double? = null
)

