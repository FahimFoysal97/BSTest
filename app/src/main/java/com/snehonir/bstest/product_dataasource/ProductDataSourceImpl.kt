package com.snehonir.bstest.product_dataasource

import com.snehonir.bstest.data.ProductDao
import com.snehonir.bstest.model.Product
import com.snehonir.bstest.repository.ApiService
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductDataSource {
    override suspend fun fetchProductsFromApi(limit: Int, skip: Int): List<Product> {
        return apiService.getProducts(limit = limit, skip = skip)
    }

    override suspend fun getCachedProducts(): List<Product> {
        return productDao.getAllProducts()
    }

    override suspend fun cacheProducts(users: List<Product>) {
        productDao.insertProducts(users)
    }
}