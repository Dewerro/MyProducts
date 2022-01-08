package com.dewerro.myproductsapp.my_products.presentation.product_selected

import com.dewerro.myproductsapp.my_products.domain.model.Category

data class ProductSelectedState(
    val categories: List<Category> = emptyList()
)