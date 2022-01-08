package com.dewerro.myproductsapp.my_products.domain.use_case

import com.dewerro.myproductsapp.my_products.domain.use_case.products.*

data class ProductsUseCases(
    val getProducts: GetProducts,
    val addProduct: AddProduct,
    val deleteProduct: DeleteProduct,
    val updateProduct: UpdateProduct,
    val getProductByCategoryName: GetProductByCategoryName
)