package com.dewerro.myproductsapp.my_products.presentation.category_selected

import com.dewerro.myproductsapp.my_products.domain.model.Product

data class CategorySelectedState(
    val products: List<Product> = emptyList(),
    val recentlyDeletedProduct: Product? = null
)