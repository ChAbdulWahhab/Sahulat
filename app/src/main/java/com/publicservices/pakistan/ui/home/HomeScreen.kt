package com.publicservices.pakistan.ui.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.publicservices.pakistan.data.model.ServiceCategory
import com.publicservices.pakistan.ui.components.CategoryChip
import com.publicservices.pakistan.ui.components.SearchBar
import com.publicservices.pakistan.ui.components.ServiceCard
import com.publicservices.pakistan.utils.IntentHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val services by viewModel.services.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { 
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(
                            id = context.resources.getIdentifier("sahulat_logo", "drawable", context.packageName)
                        ),
                        contentDescription = "Sahulat",
                        modifier = Modifier
                            .height(48.dp) // Increased from 32dp for better visibility
                            .padding(vertical = 4.dp)
                    )
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Theme Toggle
                        IconButton(
                            onClick = { viewModel.toggleTheme() }
                        ) {
                            Icon(
                                imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Toggle Theme",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Language Toggle
                        IconButton(
                            onClick = { viewModel.toggleLanguage() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Change Language",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Clean white header
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar Section
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { viewModel.updateSearchQuery(it) },
                    language = currentLanguage
                )
            }
            
            // Category Filters Section
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(ServiceCategory.values()) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = selectedCategory == category,
                        onClick = { viewModel.selectCategory(category) },
                        language = currentLanguage
                    )
                }
            }
            
            // Services List
            if (services.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (currentLanguage == "ur") "🇵🇰" else "🔍",
                            style = MaterialTheme.typography.displayLarge
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = if (currentLanguage == "ur") 
                                "کوئی خدمت نہیں ملی" 
                            else 
                                "No services found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(services, key = { it.id }) { service ->
                        ServiceCard(
                            service = service,
                            language = currentLanguage,
                            onSendSms = {
                                IntentHelper.sendSms(context, service.serviceNumber)
                            },
                            onCall = {
                                IntentHelper.makeCall(context, service.serviceNumber)
                            },
                            onCopy = {
                                IntentHelper.copyToClipboard(context, service.serviceNumber)
                            },
                            onFavoriteToggle = {
                                viewModel.toggleFavorite(service.id, service.isFavorite)
                            }
                        )
                    }
                }
            }
        }
    }
}
