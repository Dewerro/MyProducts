package com.dewerro.myproductsapp.my_products.domain.use_case.products

import com.dewerro.myproductsapp.my_products.domain.model.InvalidProductException
import com.dewerro.myproductsapp.my_products.domain.model.Product
import com.dewerro.myproductsapp.my_products.domain.repository.ProductsRepository

class AddProduct(
    private val repository: ProductsRepository
) {
    @Throws(InvalidProductException::class)
    suspend operator fun invoke(product: Product) {
        if (product.name.isBlank()) {
            throw InvalidProductException("Product name cannot be empty.")
        }
        repository.insertProduct(product)
    }
}