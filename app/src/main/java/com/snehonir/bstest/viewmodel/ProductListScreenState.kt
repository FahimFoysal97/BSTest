package com.snehonir.bstest.viewmodel

import com.snehonir.bstest.model.Product

data class ProductListScreenState(
    val productsFromAPI: List<Product> = emptyList(),
    val productsFromCache: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)