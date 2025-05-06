package com.snehonir.bstest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snehonir.bstest.repository.ProductRepository
import com.snehonir.bstest.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _productListScreenState = MutableStateFlow(ProductListScreenState())
    val productListScreenState: StateFlow<ProductListScreenState> = _productListScreenState

    fun fetchProducts(limit: Int = Constants.DEFAULT_LOAD_SIZE, skip: Int = 0) {
        setIsLoading(true)
        viewModelScope.launch {
            _productListScreenState.value =
                _productListScreenState.value.copy(isLoading = true, error = null)
            try {
                val products = repository.fetchProducts(limit = limit, skip = skip)
                _productListScreenState.value =
                    _productListScreenState.value.copy(productsFromAPI = products, isLoading = false)
                getCachedProducts()
            } catch (e: Exception) {
                _productListScreenState.value =
                    _productListScreenState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun getCachedProducts() {
        setIsLoading(true)
        viewModelScope.launch {
            _productListScreenState.value =
                _productListScreenState.value.copy(isLoading = true, error = null)
            try {
                val products = repository.getCachedProducts()
                _productListScreenState.value =
                    _productListScreenState.value.copy(productsFromCache = products, isLoading = false)
            } catch (e: Exception) {
                _productListScreenState.value =
                    _productListScreenState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun setIsLoading(isLoading: Boolean) {
        _productListScreenState.value = _productListScreenState.value.copy(isLoading = isLoading)
    }
}