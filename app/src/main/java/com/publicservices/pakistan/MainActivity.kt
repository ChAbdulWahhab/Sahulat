package com.publicservices.pakistan

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.publicservices.pakistan.data.local.ServiceDatabase
import com.publicservices.pakistan.data.repository.ServiceRepository
import com.publicservices.pakistan.ui.about.AboutScreen
import com.publicservices.pakistan.ui.favorites.FavoritesScreen
import com.publicservices.pakistan.ui.favorites.FavoritesViewModel
import com.publicservices.pakistan.ui.home.HomeScreen
import com.publicservices.pakistan.ui.home.HomeViewModel
import com.publicservices.pakistan.ui.theme.Gray
import com.publicservices.pakistan.ui.theme.PublicServiceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize database and repository
        val database = ServiceDatabase.getDatabase(applicationContext)
        val repository = ServiceRepository(database.serviceDao())
        
        setContent {
            val homeViewModel: HomeViewModel = viewModel(
                factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return HomeViewModel(repository) as T
                    }
                }
            )
            val currentLanguage by homeViewModel.currentLanguage.collectAsState()
            val isDarkMode by homeViewModel.isDarkMode.collectAsState()

            PublicServiceAppTheme(
                darkTheme = isDarkMode,
                languageCode = currentLanguage // Pass language for typography
            ) {
                MainApp(repository, homeViewModel)
            }
        }
    }
}

@Composable
fun MainApp(repository: ServiceRepository, homeViewModel: HomeViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    
    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return FavoritesViewModel(repository) as T
            }
        }
    )
    
    val currentLanguage by homeViewModel.currentLanguage.collectAsState()
    
    Scaffold(
        bottomBar = {
            GlassyBottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                currentLanguage = currentLanguage
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(bottom = 0.dp)) { // Content goes under floating bar
            when (selectedTab) {
                0 -> HomeScreen(
                    viewModel = homeViewModel,
                    modifier = Modifier.padding(bottom = 0.dp) // Specific padding handling
                )
                1 -> FavoritesScreen(
                    viewModel = favoritesViewModel,
                    currentLanguage = currentLanguage,
                    modifier = Modifier.padding(bottom = 0.dp)
                )
                2 -> AboutScreen(
                    currentLanguage = currentLanguage,
                    modifier = Modifier.padding(bottom = 0.dp)
                )
            }
        }
    }
}

@Composable
fun GlassyBottomNavBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    currentLanguage: String
) {
    // Check if dark mode by comparing background color
    val isDarkMode = MaterialTheme.colorScheme.background == Color(0xFF030712)
    
    // Glassy blur effect - works on API 31+ with RenderEffect, fallback for older versions
    val blurModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Modifier.graphicsLayer {
            renderEffect = RenderEffect
                .createBlurEffect(25f, 25f, Shader.TileMode.CLAMP)
                .asComposeRenderEffect()
        }
    } else {
        Modifier // Fallback: use semi-transparent background
    }
    
    // Background color based on theme - glassy effect with transparency
    val glassyBackground = if (isDarkMode) {
        // Dark mode - semi-transparent dark gray
        Color(0xFF1F2937).copy(alpha = 0.85f)
    } else {
        // Light mode - semi-transparent white
        Color(0xFFFFFFFF).copy(alpha = 0.85f)
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // Glassy background with blur
        Surface(
            modifier = blurModifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(24.dp),
            color = glassyBackground,
            tonalElevation = 8.dp,
            shadowElevation = 12.dp,
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
            )
        ) {
            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = 0.dp,
                modifier = Modifier.height(72.dp)
            ) {
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Home, 
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        ) 
                    },
                    label = { 
                        Text(
                            if (currentLanguage == "ur") "ہوم" else "Home", 
                            style = MaterialTheme.typography.labelSmall
                        ) 
                    },
                    selected = selectedTab == 0,
                    onClick = { onTabSelected(0) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Gray,
                        unselectedTextColor = Gray,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                )
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Favorite, 
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        ) 
                    },
                    label = { 
                        Text(
                            if (currentLanguage == "ur") "پسندیدہ" else "Favorites", 
                            style = MaterialTheme.typography.labelSmall
                        ) 
                    },
                    selected = selectedTab == 1,
                    onClick = { onTabSelected(1) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Gray,
                        unselectedTextColor = Gray,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                )
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Info, 
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        ) 
                    },
                    label = { 
                        Text(
                            if (currentLanguage == "ur") "معلومات" else "About", 
                            style = MaterialTheme.typography.labelSmall
                        ) 
                    },
                    selected = selectedTab == 2,
                    onClick = { onTabSelected(2) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Gray,
                        unselectedTextColor = Gray,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                )
            }
        }
    }
}
