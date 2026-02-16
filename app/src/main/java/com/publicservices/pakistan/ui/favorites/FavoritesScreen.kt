package com.publicservices.pakistan.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.publicservices.pakistan.ui.components.ServiceCard
import com.publicservices.pakistan.utils.IntentHelper

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    currentLanguage: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val favoriteServices by viewModel.favoriteServices.collectAsState()
    
    viewModel.setLanguage(currentLanguage)
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        if (favoriteServices.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (currentLanguage == "ur") 
                            "کوئی پسندیدہ خدمت نہیں" 
                        else 
                            "No favorite services",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (currentLanguage == "ur") 
                            "خدمات کو پسندیدہ میں شامل کرنے کے لیے دل کے آئیکن پر ٹیپ کریں" 
                        else 
                            "Tap the heart icon to add services to favorites",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(favoriteServices, key = { it.id }) { service ->
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
