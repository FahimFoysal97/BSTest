package com.snehonir.bstest.product_dataasource

import com.snehonir.bstest.model.Product

interface ProductDataSource {
    suspend fun fetchProductsFromApi(limit: Int, skip: Int): List<Product>
    suspend fun getCachedProducts(): List<Product>
    suspend fun cacheProducts(products: List<Product>)
}