package com.publicservices.pakistan.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.publicservices.pakistan.data.model.Service
import com.publicservices.pakistan.data.repository.ServiceRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: ServiceRepository) : ViewModel() {
    
    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()
    
    val favoriteServices: StateFlow<List<Service>> = repository.getFavoriteServices()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun setLanguage(language: String) {
        _currentLanguage.value = language
    }
    
    fun toggleFavorite(serviceId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(serviceId, !isFavorite)
        }
    }
}
