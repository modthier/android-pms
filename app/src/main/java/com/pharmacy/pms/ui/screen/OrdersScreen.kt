package com.pharmacy.pms.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pharmacy.pms.data.local.TokenManager
import com.pharmacy.pms.data.repository.OrderRepository
import com.pharmacy.pms.ui.viewmodel.OrderViewModel

@Composable
fun OrdersScreen() {
    val context = LocalContext.current
    val viewModel: OrderViewModel = viewModel(
        factory = remember(context) {
            object : androidx.lifecycle.ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    val tokenManager = TokenManager(context)
                    val orderRepository = OrderRepository(tokenManager)
                    @Suppress("UNCHECKED_CAST")
                    return OrderViewModel(orderRepository) as T
                }
            }
        }
    )
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Orders") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Create new order */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "New Order")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.orders.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text("No orders found")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.orders) { order ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Order #${order.id}",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "Total: $${order.total}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                if (order.discount != null && order.discount > 0) {
                                    Text(
                                        text = "Discount: $${order.discount}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Text(
                                    text = "Items: ${order.items?.size ?: 0}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                if (order.createdAt != null) {
                                    Text(
                                        text = "Date: ${order.createdAt}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            if (uiState.errorMessage != null) {
                Snackbar(
                    action = {
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("Dismiss")
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(uiState.errorMessage)
                }
            }
        }
    }
}
