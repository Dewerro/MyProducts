package com.dewerro.myproductsapp.my_products.presentation.product_list

import com.dewerro.myproductsapp.my_products.domain.model.Product

data class ProductsListState(
    val products: List<Product> = emptyList(),
    val recentlyDeletedProduct: Product? = null
)
