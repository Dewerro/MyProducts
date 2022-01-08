package com.dewerro.myproductsapp.my_products.presentation.categories_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dewerro.myproductsapp.my_products.domain.use_case.CategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CategoriesListState())
    val state: State<CategoriesListState> = _state

    private var getCategoriesJob: Job? = null

    init {
        getCategories()
    }

    fun onEvent(event: CategoriesListEvent) {
        when (event) {
            is CategoriesListEvent.AddCategory -> {
                viewModelScope.launch {
                    categoriesUseCase.addCategory.invoke(event.category)
                }
            }
            is CategoriesListEvent.DeleteCategory -> {
                viewModelScope.launch {
                    categoriesUseCase.deleteCategory.invoke(event.category)
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