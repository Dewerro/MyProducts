package com.dewerro.myproductsapp.my_products.domain.use_case.categories

import com.dewerro.myproductsapp.my_products.domain.model.Category
import com.dewerro.myproductsapp.my_products.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow

class GetCategories(
    private val repository: CategoriesRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return repository.getCategories()
    }
}