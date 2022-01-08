package com.dewerro.myproductsapp.my_products.domain.use_case.categories

import com.dewerro.myproductsapp.my_products.domain.model.Category
import com.dewerro.myproductsapp.my_products.domain.repository.CategoriesRepository

class DeleteCategory(
    val repository: CategoriesRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.deleteCategory(category)
    }
}