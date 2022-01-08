package com.dewerro.myproductsapp.my_products.domain.use_case.products

import com.dewerro.myproductsapp.my_products.domain.model.Product
import com.dewerro.myproductsapp.my_products.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetProductByCategoryName(
    private val repository: ProductsRepository
) {
    operator fun invoke(categoryName: String): Flow<List<Product>> {
        return repository.getProductByCategoryName(categoryName)
    }
}