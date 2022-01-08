package com.dewerro.myproductsapp.my_products.domain.repository

import com.dewerro.myproductsapp.my_products.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProducts(): Flow<List<Product>>

    fun getProductByCategoryName(categoryName: String): Flow<List<Product>>

    suspend fun insertProduct(product: Product)

    suspend fun deleteProduct(product: Product)

    suspend fun updateProduct(id: Int, name: String, category: String, description: String)
}