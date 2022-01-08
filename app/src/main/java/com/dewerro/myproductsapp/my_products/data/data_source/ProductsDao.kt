package com.dewerro.myproductsapp.my_products.data.data_source

import androidx.room.*
import com.dewerro.myproductsapp.my_products.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Query("SELECT * FROM products_table")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products_table WHERE category = :categoryName")
    fun getProductByCategoryName(categoryName: String): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("UPDATE products_table SET name = :name, category = :category, description = :description WHERE id = :id")
    suspend fun updateProduct(id: Int, name: String, category: String, description: String)
}