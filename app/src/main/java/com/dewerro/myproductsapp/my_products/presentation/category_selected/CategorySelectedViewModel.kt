package com.dewerro.myproductsapp.my_products.presentation.category_selected

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dewerro.myproductsapp.my_products.domain.model.Category
import com.dewerro.myproductsapp.my_products.domain.use_case.CategoriesUseCase
import com.dewerro.myproductsapp.my_products.domain.use_case.ProductsUseCases
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySelectedViewModel @Inject constructor(
    private val productsUseCases: ProductsUseCases,
    private val categoriesUseCase: CategoriesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(CategorySelectedState())
    val state: State<CategorySelectedState> = _state

    private var getProductsJob: Job? = null

    init {
        getProductByCategoryName()
    }

    fun onEvent(event: CategorySelectedEvent) {
        when (event) {
            is CategorySelectedEvent.UpdateCategory -> {
                viewModelScope.launch {
                    categoriesUseCase.updateCategory(
                        event.id,
                        event.name,
                        event.description
                    )
                }
            }
            is CategorySelectedEvent.DeleteProduct -> {
                viewModelScope.launch {
                    productsUseCases.deleteProduct(event.product)
                }
            }
            is CategorySelectedEvent.AddProduct -> {
                viewModelScope.launch {
                    productsUseCases.addProduct(event.product)
                }
            }
        }
    }

    private fun getProductByCategoryName() {
        val category =
            Gson().fromJson(savedStateHandle.get<String>("category"), Category::class.java)
        getProductsJob?.cancel()
        getProductsJob =
            productsUseCases.getProductByCategoryName(category.name)
                .onEach { products ->
                    _state.value = state.value.copy(
                        products = products
                    )
                }.launchIn(viewModelScope)
    }
}