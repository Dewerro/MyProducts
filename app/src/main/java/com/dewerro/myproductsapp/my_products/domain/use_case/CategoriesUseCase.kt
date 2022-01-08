package com.dewerro.myproductsapp.my_products.domain.use_case

import com.dewerro.myproductsapp.my_products.domain.use_case.categories.AddCategory
import com.dewerro.myproductsapp.my_products.domain.use_case.categories.DeleteCategory
import com.dewerro.myproductsapp.my_products.domain.use_case.categories.GetCategories
import com.dewerro.myproductsapp.my_products.domain.use_case.categories.UpdateCategory

data class CategoriesUseCase(
    val getCategories: GetCategories,
    val addCategory: AddCategory,
    val deleteCategory: DeleteCategory,
    val updateCategory: UpdateCategory
)