package com.snehonir.bstest.repository

import com.snehonir.bstest.model.Product
import com.snehonir.bstest.product_dataasource.ProductDataSource
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDataSource: ProductDataSource
) {
    suspend fun fetchProducts(limit: Int, skip: Int): List<Product> {
        val products = productDataSource.fetchProductsFromApi(limit = limit, skip = skip)
        productDataSource.cacheProducts(products)
        return products
    }

    suspend fun getCachedProducts(): List<Product> {
        return productDataSource.getCachedProducts()
    }
}