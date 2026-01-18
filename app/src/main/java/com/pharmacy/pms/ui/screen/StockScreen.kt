package com.pharmacy.pms.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pharmacy.pms.data.local.TokenManager
import com.pharmacy.pms.data.repository.StockRepository
import com.pharmacy.pms.ui.viewmodel.StockViewModel

@Composable
fun StockScreen() {
    val context = LocalContext.current
    val viewModel: StockViewModel = viewModel(
        factory = remember(context) {
            object : androidx.lifecycle.ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    val tokenManager = TokenManager(context)
                    val stockRepository = StockRepository(tokenManager)
                    @Suppress("UNCHECKED_CAST")
                    return StockViewModel(stockRepository) as T
                }
            }
        }
    )
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock") },
                actions = {
                    TextButton(onClick = { viewModel.loadAvailableStock() }) {
                        Text("Available")
                    }
                    TextButton(onClick = { viewModel.loadExpiredStock() }) {
                        Text("Expired")
                    }
                    TextButton(onClick = { viewModel.loadStock() }) {
                        Text("All")
                    }
                }
            )
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
            } else if (uiState.stock.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text("No stock items found")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.stock) { stock ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = stock.drug?.tradeName ?: "Unknown Drug",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "Quantity: ${stock.quantity}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                if (stock.expiryDate != null) {
                                    Text(
                                        text = "Expiry: ${stock.expiryDate}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                if (stock.sellingPrice != null) {
                                    Text(
                                        text = "Price: $${stock.sellingPrice}",
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
