package com.dewerro.myproductsapp.my_products.domain.use_case.products

import com.dewerro.myproductsapp.my_products.domain.model.Product
import com.dewerro.myproductsapp.my_products.domain.repository.ProductsRepository

class DeleteProduct(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.deleteProduct(product)
    }
}