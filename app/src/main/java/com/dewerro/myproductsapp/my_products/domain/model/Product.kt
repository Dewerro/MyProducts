package com.dewerro.myproductsapp.my_products.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class Product(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val category: String = "unsorted",
    val description: String
)

class InvalidProductException(message: String) : Exception(message)