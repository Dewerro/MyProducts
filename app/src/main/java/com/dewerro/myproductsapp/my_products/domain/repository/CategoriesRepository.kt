package com.dewerro.myproductsapp.my_products.domain.repository

import com.dewerro.myproductsapp.my_products.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getCategories(): Flow<List<Category>>

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun updateCategory(id: Int, name: String, description: String)
}