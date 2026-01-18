package com.pharmacy.pms.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pharmacy.pms.data.model.Stock
import com.pharmacy.pms.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StockUiState(
    val stock: List<Stock> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showAvailableOnly: Boolean = false,
    val showExpiredOnly: Boolean = false
)

class StockViewModel(
    private val stockRepository: StockRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StockUiState())
    val uiState: StateFlow<StockUiState> = _uiState.asStateFlow()
    
    init {
        loadStock()
    }
    
    fun loadStock() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            stockRepository.getStock()
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        stock = response.data,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to load stock"
                    )
                }
        }
    }
    
    fun loadAvailableStock() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                showAvailableOnly = true,
                showExpiredOnly = false
            )
            
            stockRepository.getAvailableStock()
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        stock = response.data,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to load available stock"
                    )
                }
        }
    }
    
    fun loadExpiredStock(months: Int = 3) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                showAvailableOnly = false,
                showExpiredOnly = true
            )
            
            stockRepository.getExpiredStock(months = months)
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        stock = response.data,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to load expired stock"
                    )
                }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
