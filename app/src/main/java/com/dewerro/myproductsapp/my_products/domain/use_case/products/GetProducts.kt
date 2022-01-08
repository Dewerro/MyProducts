package com.dewerro.myproductsapp.my_products.domain.use_case.products

import com.dewerro.myproductsapp.my_products.domain.model.Product
import com.dewerro.myproductsapp.my_products.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetProducts(
    private val repository: ProductsRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getProducts()
    }
}