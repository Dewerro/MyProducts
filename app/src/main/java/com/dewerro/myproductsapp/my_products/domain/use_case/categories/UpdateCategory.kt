package com.dewerro.myproductsapp.my_products.domain.use_case.categories

import com.dewerro.myproductsapp.my_products.domain.model.InvalidCategoryException
import com.dewerro.myproductsapp.my_products.domain.repository.CategoriesRepository

class UpdateCategory(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(id: Int, name: String, description: String) {
        if (name.isBlank()) {
            throw InvalidCategoryException("Category name cannot be empty.")
        }
        repository.updateCategory(id, name, description)
    }
}