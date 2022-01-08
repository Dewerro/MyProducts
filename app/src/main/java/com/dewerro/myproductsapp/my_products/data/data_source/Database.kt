package com.dewerro.myproductsapp.my_products.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dewerro.myproductsapp.my_products.domain.model.Category
import com.dewerro.myproductsapp.my_products.domain.model.Product

@Database(
    entities = [
        Product::class,
        Category::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract val productsDao: ProductsDao
    abstract val categoriesDao: CategoriesDao

    companion object {
        const val DATABASE_NAME = "main_db"
    }
}