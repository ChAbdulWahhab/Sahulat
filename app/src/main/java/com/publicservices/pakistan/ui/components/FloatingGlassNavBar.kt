package com.publicservices.pakistan.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FloatingGlassNavBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    currentLanguage: String,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        TabItem(Icons.Default.Home, if (currentLanguage == "ur") "ہوم" else "Home"),
        TabItem(Icons.Default.Favorite, if (currentLanguage == "ur") "پسندیدہ" else "Favorites"),
        TabItem(Icons.Default.Info, if (currentLanguage == "ur") "معلومات" else "About")
    )
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
        shadowElevation = 12.dp,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, item ->
                val selected = selectedTab == index
                Surface(
                    onClick = { onTabSelected(index) },
                    shape = CircleShape,
                    color = if (selected) 
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f) 
                    else 
                        Color.Transparent,
                    modifier = Modifier.size(56.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selected) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

private data class TabItem(val icon: ImageVector, val label: String)
