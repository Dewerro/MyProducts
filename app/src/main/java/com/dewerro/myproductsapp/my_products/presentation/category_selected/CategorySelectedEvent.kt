package com.dewerro.myproductsapp.my_products.presentation.category_selected

import com.dewerro.myproductsapp.my_products.domain.model.Product

sealed class CategorySelectedEvent {
    data class DeleteProduct(val product: Product) : CategorySelectedEvent()
    data class AddProduct(val product: Product) : CategorySelectedEvent()
    data class UpdateCategory(
        val id: Int,
        val name: String,
        val description: String
    ) : CategorySelectedEvent()
}