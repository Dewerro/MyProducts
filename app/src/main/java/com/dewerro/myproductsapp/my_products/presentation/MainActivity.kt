package com.dewerro.myproductsapp.my_products.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dewerro.myproductsapp.my_products.domain.model.Category
import com.dewerro.myproductsapp.my_products.domain.model.Product
import com.dewerro.myproductsapp.my_products.presentation.categories_list.CategoriesListScreen
import com.dewerro.myproductsapp.my_products.presentation.category_selected.CategorySelectedScreen
import com.dewerro.myproductsapp.my_products.presentation.product_list.ProductsListScreen
import com.dewerro.myproductsapp.my_products.presentation.product_selected.ProductSelectedScreen
import com.dewerro.myproductsapp.my_products.presentation.select_option.SelectOptionScreen
import com.dewerro.myproductsapp.my_products.presentation.util.Screen
import com.dewerro.myproductsapp.ui.theme.MyProductsTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                MyProductsTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screen.SelectOptionScreen.route
                        ) {
                            composable(
                                route = Screen.SelectOptionScreen.route
                            ) {
                                SelectOptionScreen(navController)
                            }
                            composable(
                                route = Screen.CategoriesListScreen.route
                            ) {
                                CategoriesListScreen(navController)
                            }
                            composable(
                                route = Screen.CategorySelectedScreen.route + "/{category}",
                                arguments = listOf(
                                    navArgument("category") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                backStackEntry.arguments?.getString("category")?.let { json ->
                                    val category = Gson().fromJson(json, Category::class.java)
                                    CategorySelectedScreen(navController, category)
                                }
                            }
                            composable(
                                route = Screen.ProductsListScreen.route
                            ) {
                                ProductsListScreen(navController)
                            }
                            composable(
                                route = Screen.ProductSelectedScreen.route + "/{product}",
                                arguments = listOf(
                                    navArgument("product") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                backStackEntry.arguments?.getString("product")?.let { json ->
                                    val product = Gson().fromJson(json, Product::class.java)
                                    ProductSelectedScreen(product)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}