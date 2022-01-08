package com.dewerro.myproductsapp.my_products.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey val id: Int?,
    var name: String,
    var description: String
)

class InvalidCategoryException(message: String) : Exception(message)