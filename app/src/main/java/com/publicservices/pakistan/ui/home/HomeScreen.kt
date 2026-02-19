package com.publicservices.pakistan.ui.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.publicservices.pakistan.data.model.ServiceCategory
import com.publicservices.pakistan.ui.components.CategoryChip
import com.publicservices.pakistan.ui.components.DidYouKnowCarousel
import com.publicservices.pakistan.ui.components.SearchBar
import com.publicservices.pakistan.ui.components.SearchHistoryChip
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
    val searchHistory by viewModel.searchHistory.collectAsState()
    var searchExpanded by remember { mutableStateOf(false) }
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { 
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(
                                id = context.resources.getIdentifier("only_sahulat_logo_transparent", "drawable", context.packageName)
                            ),
                            contentDescription = "Sahulat",
                            modifier = Modifier.height(40.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Sahulat",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 0.5.sp
                        )
                    }
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

                        // Search (opens expandable search bar)
                        IconButton(
                            onClick = { searchExpanded = !searchExpanded }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = if (currentLanguage == "ur") "تلاش" else "Search",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Language Toggle (Translate icon - EN/اردو)
                        IconButton(
                            onClick = { viewModel.toggleLanguage() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = if (currentLanguage == "ur") "زبان بدلیں (انگریزی)" else "Change Language (Urdu)",
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
            // Expandable Search Bar (animate in/out; thin when visible)
            AnimatedVisibility(
                visible = searchExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = { viewModel.updateSearchQuery(it) },
                            language = currentLanguage,
                            compact = true,
                            onDismiss = { searchExpanded = false }
                        )
                        if (searchQuery.isEmpty() && searchHistory.isNotEmpty()) {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                items(searchHistory) { historyItem ->
                                    SearchHistoryChip(
                                        query = historyItem,
                                        onClick = { viewModel.selectFromHistory(historyItem) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Did You Know Carousel (key so font/language updates immediately)
            key(currentLanguage) {
                DidYouKnowCarousel(
                    language = currentLanguage,
                    modifier = Modifier.padding(vertical = 8.dp)
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
                        androidx.compose.animation.AnimatedVisibility(
                            visible = true,
                            enter = androidx.compose.animation.fadeIn(
                                animationSpec = androidx.compose.animation.core.tween(500)
                            ) + androidx.compose.animation.slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = androidx.compose.animation.core.tween(500)
                            )
                        ) {
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
}
