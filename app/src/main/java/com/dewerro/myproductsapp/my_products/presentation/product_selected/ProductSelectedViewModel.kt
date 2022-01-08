package com.dewerro.myproductsapp.my_products.presentation.product_selected

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dewerro.myproductsapp.my_products.domain.use_case.CategoriesUseCase
import com.dewerro.myproductsapp.my_products.domain.use_case.ProductsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSelectedViewModel @Inject constructor(
    private val productsUseCases: ProductsUseCases,
    private val categoriesUseCase: CategoriesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProductSelectedState())
    val state: State<ProductSelectedState> = _state

    private var getCategoriesJob: Job? = null

    init {
        getCategories()
    }

    fun onEvent(event: ProductSelectedEvent) {
        when (event) {
            is ProductSelectedEvent.UpdateProduct -> {
                viewModelScope.launch {
                    productsUseCases.updateProduct(
                        event.id,
                        event.name,
                        event.category,
                        event.description
                    )
                }
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = categoriesUseCase.getCategories()
            .onEach { categories ->
                _state.value = state.value.copy(
                    categories = categories
                )
            }
            .launchIn(viewModelScope)
    }
}