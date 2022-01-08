package com.dewerro.myproductsapp.my_products.presentation.categories_list

import com.dewerro.myproductsapp.my_products.domain.model.Category

sealed class CategoriesListEvent {
    data class AddCategory(val category: Category) : CategoriesListEvent()
    data class DeleteCategory(val category: Category) : CategoriesListEvent()
}