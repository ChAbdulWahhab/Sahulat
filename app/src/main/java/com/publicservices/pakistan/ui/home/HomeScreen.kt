package com.publicservices.pakistan.ui.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Translate
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
    val services by viewModel.services.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val hasScrolled by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset > 4 || listState.firstVisibleItemIndex > 0 }
    }
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(
                                id = context.resources.getIdentifier("only_sahulat_logo_transparent", "drawable", context.packageName)
                            ),
                            contentDescription = null,
                            modifier = Modifier.height(32.dp)
                        )
                        Text(
                            text = "Sahulat",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (isDarkMode) "Light mode" else "Dark mode",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { viewModel.toggleLanguage() }) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = if (currentLanguage == "ur") "English" else "Urdu",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        val searchBarHeight = 100.dp
        @Composable
        fun SearchBarContent() {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = { viewModel.updateSearchQuery(it) },
                            language = currentLanguage,
                            compact = true,
                            onDismiss = null
                        )
                    }
                    if (searchQuery.isEmpty() && searchHistory.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
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
                if (hasScrolled) {
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                        thickness = 1.dp
                    )
                }
            }
        }
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Scrollable content: Did You Know → Search Bar (inline) → Categories → Services
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    key(currentLanguage) {
                        DidYouKnowCarousel(language = currentLanguage, modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
                item(key = "search_bar") {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Transparent
                    ) {
                        SearchBarContent()
                    }
                }
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
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
                }
                if (services.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = if (currentLanguage == "ur") "🇵🇰" else "🔍", style = MaterialTheme.typography.displayLarge)
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    text = if (currentLanguage == "ur") "کوئی خدمت نہیں ملی" else "No services found",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                } else {
                    items(services, key = { it.id }) { service ->
                        ServiceCard(
                            service = service,
                            language = currentLanguage,
                            onSendSms = { IntentHelper.sendSms(context, service.serviceNumber) },
                            onCall = { IntentHelper.makeCall(context, service.serviceNumber) },
                            onCopy = { IntentHelper.copyToClipboard(context, service.serviceNumber) },
                            onFavoriteToggle = { viewModel.toggleFavorite(service.id, service.isFavorite) }
                        )
                    }
                }
            }
            // Sticky overlay: visible only when scrolled past search bar; zIndex + shadow
            androidx.compose.animation.AnimatedVisibility(
                visible = hasScrolled,
                enter = androidx.compose.animation.fadeIn(androidx.compose.animation.core.tween(100)),
                exit = androidx.compose.animation.fadeOut(androidx.compose.animation.core.tween(100))
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(searchBarHeight)
                        .zIndex(1f)
                        .align(Alignment.TopCenter),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 6.dp
                ) {
                    SearchBarContent()
                }
            }
        }
    }
}
