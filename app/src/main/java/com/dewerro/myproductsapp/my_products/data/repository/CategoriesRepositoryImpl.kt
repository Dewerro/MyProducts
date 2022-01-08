package com.dewerro.myproductsapp.my_products.data.repository

import com.dewerro.myproductsapp.my_products.data.data_source.CategoriesDao
import com.dewerro.myproductsapp.my_products.domain.model.Category
import com.dewerro.myproductsapp.my_products.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow

class CategoriesRepositoryImpl(
    private val categoriesDao: CategoriesDao
) : CategoriesRepository {
    override fun getCategories(): Flow<List<Category>> {
        return categoriesDao.getCategories()
    }

    override suspend fun insertCategory(category: Category) {
        categoriesDao.insertCategory(category)
    }

    override suspend fun deleteCategory(category: Category) {
        categoriesDao.deleteCategory(category)
    }

    override suspend fun updateCategory(id: Int, name: String, description: String) {
        categoriesDao.updateCategory(id, name, description)
    }
}