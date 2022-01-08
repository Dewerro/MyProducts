package com.dewerro.myproductsapp.my_products.domain.use_case.categories

import com.dewerro.myproductsapp.my_products.domain.model.Category
import com.dewerro.myproductsapp.my_products.domain.model.InvalidCategoryException
import com.dewerro.myproductsapp.my_products.domain.repository.CategoriesRepository

class AddCategory(
    private val repository: CategoriesRepository
) {
    @Throws(InvalidCategoryException::class)
    suspend operator fun invoke(category: Category) {
        if (category.name.isBlank()) {
            throw InvalidCategoryException("Category name cannot be empty.")
        }
        repository.insertCategory(category)
    }
}