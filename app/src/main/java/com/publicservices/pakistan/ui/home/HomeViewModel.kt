package com.publicservices.pakistan.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.publicservices.pakistan.data.model.Service
import com.publicservices.pakistan.data.model.ServiceCategory
import com.publicservices.pakistan.data.repository.ServiceRepository
import com.publicservices.pakistan.utils.PreferencesManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val repository: ServiceRepository,
    private val context: Context? = null
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow(ServiceCategory.ALL)
    val selectedCategory: StateFlow<ServiceCategory> = _selectedCategory.asStateFlow()
    
    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()
    
    val searchHistory: StateFlow<List<String>> = flow {
        if (context != null) {
            emit(PreferencesManager.getSearchHistory(context).take(8))
        } else {
            emit(emptyList())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    val services: StateFlow<List<Service>> = combine(
        _searchQuery,
        _selectedCategory
    ) { query, category ->
        Pair(query, category)
    }.flatMapLatest { (query, category) ->
        when {
            query.isNotEmpty() -> repository.searchServices(query)
            category != ServiceCategory.ALL -> repository.getServicesByCategory(category)
            else -> repository.getAllServices()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        // Save to search history if query is not empty
        if (query.isNotBlank() && context != null) {
            PreferencesManager.addToSearchHistory(context, query)
        }
    }
    
    fun selectFromHistory(query: String) {
        _searchQuery.value = query
        if (context != null) {
            PreferencesManager.addToSearchHistory(context, query)
        }
    }
    
    fun selectCategory(category: ServiceCategory) {
        _selectedCategory.value = category
        _searchQuery.value = "" // Clear search when category changes
    }
    
    fun toggleLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == "en") "ur" else "en"
    }

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }
    
    fun toggleFavorite(serviceId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(serviceId, !isFavorite)
        }
    }
}
