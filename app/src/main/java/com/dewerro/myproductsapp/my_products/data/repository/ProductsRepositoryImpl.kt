package com.dewerro.myproductsapp.my_products.data.repository

import com.dewerro.myproductsapp.my_products.data.data_source.ProductsDao
import com.dewerro.myproductsapp.my_products.domain.model.Product
import com.dewerro.myproductsapp.my_products.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class ProductsRepositoryImpl(
    private val dao: ProductsDao
) : ProductsRepository {
    override fun getProducts(): Flow<List<Product>> {
        return dao.getProducts()
    }

    override fun getProductByCategoryName(categoryName: String): Flow<List<Product>> {
        return dao.getProductByCategoryName(categoryName)
    }

    override suspend fun insertProduct(product: Product) {
        dao.insertProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        dao.deleteProduct(product)
    }

    override suspend fun updateProduct(
        id: Int,
        name: String,
        category: String,
        description: String
    ) {
        dao.updateProduct(id, name, category, description)
    }
}