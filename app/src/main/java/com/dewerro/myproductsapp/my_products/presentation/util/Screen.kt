package com.dewerro.myproductsapp.my_products.presentation.util

sealed class Screen(val route: String) {
    object SelectOptionScreen : Screen("select_option_screen")
    object CategoriesListScreen : Screen("categories_list_screen")
    object ProductsListScreen : Screen("products_list_screen")
    object ProductSelectedScreen : Screen("product_selected_screen")
    object CategorySelectedScreen : Screen("category_selected_screen")
}