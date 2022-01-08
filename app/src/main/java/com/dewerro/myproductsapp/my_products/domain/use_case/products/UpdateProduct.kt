package com.dewerro.myproductsapp.my_products.domain.use_case.products

import com.dewerro.myproductsapp.my_products.domain.model.InvalidProductException
import com.dewerro.myproductsapp.my_products.domain.repository.ProductsRepository

class UpdateProduct(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(id: Int, name: String, category: String, description: String) {
        if (name.isBlank()) {
            throw InvalidProductException("Product name cannot be empty.")
        }
        repository.updateProduct(id, name, category, description)
    }
}