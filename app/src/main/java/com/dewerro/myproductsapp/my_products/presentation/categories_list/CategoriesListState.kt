package com.dewerro.myproductsapp.my_products.presentation.categories_list

import com.dewerro.myproductsapp.my_products.domain.model.Category

data class CategoriesListState(
    val categories: List<Category> = emptyList(),
    val recentlyDeletedCategory: Category? = null
)