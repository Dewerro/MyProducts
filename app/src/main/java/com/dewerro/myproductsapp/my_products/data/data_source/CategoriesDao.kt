package com.dewerro.myproductsapp.my_products.data.data_source

import androidx.room.*
import com.dewerro.myproductsapp.my_products.domain.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories_table")
    fun getCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("UPDATE categories_table SET name = :name, description = :description WHERE id = :id")
    suspend fun updateCategory(id: Int, name: String, description: String)
}