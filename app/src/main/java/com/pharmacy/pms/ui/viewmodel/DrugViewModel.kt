package com.pharmacy.pms.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pharmacy.pms.data.model.Drug
import com.pharmacy.pms.data.repository.DrugRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DrugUiState(
    val drugs: List<Drug> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)

class DrugViewModel(
    private val drugRepository: DrugRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DrugUiState())
    val uiState: StateFlow<DrugUiState> = _uiState.asStateFlow()
    
    init {
        loadDrugs()
    }
    
    fun loadDrugs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            drugRepository.getDrugs()
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        drugs = response.data,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to load drugs"
                    )
                }
        }
    }
    
    fun searchDrugs(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                searchQuery = query
            )
            
            if (query.isBlank()) {
                loadDrugs()
                return@launch
            }
            
            drugRepository.searchDrugs(query)
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        drugs = response.data,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Search failed"
                    )
                }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
