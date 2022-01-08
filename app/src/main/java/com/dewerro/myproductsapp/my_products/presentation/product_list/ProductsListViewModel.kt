package com.dewerro.myproductsapp.my_products.presentation.product_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dewerro.myproductsapp.my_products.domain.use_case.ProductsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val productsUseCases: ProductsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ProductsListState())
    val state: State<ProductsListState> = _state

    private var getProductsJob: Job? = null

    init {
        getProducts()
    }

    fun onEvent(event: ProductsListEvent) {
        when (event) {
            is ProductsListEvent.AddProduct -> {
                viewModelScope.launch {
                    productsUseCases.addProduct(event.product)
                }
            }
            is ProductsListEvent.DeleteProduct -> {
                viewModelScope.launch {
                    productsUseCases.deleteProduct(event.product)
                }
            }
        }
    }

    private fun getProducts() {
        getProductsJob?.cancel()
        getProductsJob = productsUseCases.getProducts()
            .onEach { products ->
                _state.value = state.value.copy(products = products)
            }
            .launchIn(viewModelScope)
    }
}