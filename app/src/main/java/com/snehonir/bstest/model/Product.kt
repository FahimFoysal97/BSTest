package com.snehonir.bstest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val title: String,
    val price: Double,
    val discountedPrice: Double?,
    val rating: Double,
    val category: String,
    val stock: Int,
)