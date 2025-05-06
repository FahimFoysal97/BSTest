package com.snehonir.bstest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehonir.bstest.model.Product
import com.snehonir.bstest.ui.component.ErrorDialog
import com.snehonir.bstest.ui.component.ProductCard
import com.snehonir.bstest.utils.Constants
import com.snehonir.bstest.viewmodel.ProductScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductScreenViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val state = viewModel.productListScreenState.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchProducts()
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        when {
            state.value.isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            state.value.error != null -> ErrorDialog(
                errorMessage = "Something went wrong",
                onRetry = { viewModel.fetchProducts() }
            )

            else ->{
                val pullState = rememberPullToRefreshState()
                PullToRefreshBox(
                    isRefreshing = state.value.isLoading,
                    onRefresh = {
                        viewModel.fetchProducts()
                    },
                    state = pullState,
                    modifier = modifier
                ) {
                    ProductsList(
                        products = state.value.productsFromAPI,
                        loadMoreItems = {
                            if (!state.value.isLoading) {
                                viewModel.fetchProducts(
                                    limit = state.value.productsFromAPI.size + Constants.DEFAULT_LOAD_SIZE,
                                )
                            }
                        },
                    )
                }
            }

        }
    }
}

@Composable
fun ProductsList(
    products: List<Product>,
    loadMoreItems: () -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        if (products.isEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No products available",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        } else items(products.size) { index ->
            ProductCard(products[index])
            if (index == products.size - 1) {
                loadMoreItems()
            }
        }
    }
}