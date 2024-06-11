package com.example.tubescoba

data class OrderLogModel(
    val id: String? = null,
    val customerName: String? = null,
    val items: Map<String, OrderItemModel>? = null,
    val orderDate: String? = null,
    val totalPrice: Double? = null
)
