package com.snehonir.bstest.di

import android.content.Context
import androidx.room.Room
import com.snehonir.bstest.data.AppDatabase
import com.snehonir.bstest.data.ProductDao
import com.snehonir.bstest.product_dataasource.ProductDataSource
import com.snehonir.bstest.product_dataasource.ProductDataSourceImpl
import com.snehonir.bstest.repository.ApiService
import com.snehonir.bstest.utils.interceptors.CacheInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize = 10 * 1024 * 1024
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(CacheInterceptor())
            .cache(cache)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppDataSource(
        apiService: ApiService,
        productDao: ProductDao
    ): ProductDataSource {
        return ProductDataSourceImpl(apiService, productDao)
    }

    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }
}