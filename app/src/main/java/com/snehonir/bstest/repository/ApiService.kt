package com.snehonir.bstest.repository

import com.snehonir.bstest.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int = 0,
        @Query("sortBy") sortBy: String = "price,title",
        @Query("order") order: String = "asc"
    ): List<Product>
}