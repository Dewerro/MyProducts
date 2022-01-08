package com.dewerro.myproductsapp.my_products.presentation.product_selected

sealed class ProductSelectedEvent {
    data class UpdateProduct(
        val id: Int,
        val name: String,
        val category: String,
        val description: String
    ) : ProductSelectedEvent()
}