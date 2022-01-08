package com.dewerro.myproductsapp.my_products.presentation.product_list

import com.dewerro.myproductsapp.my_products.domain.model.Product

sealed class ProductsListEvent {
    data class AddProduct(val product: Product) : ProductsListEvent()
    data class DeleteProduct(val product: Product) : ProductsListEvent()
}