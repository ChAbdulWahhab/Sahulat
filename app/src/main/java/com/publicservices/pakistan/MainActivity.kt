package com.publicservices.pakistan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
            val isDarkMode by homeViewModel.isDarkMode.collectAsState()
            val currentLanguage by homeViewModel.currentLanguage.collectAsState()

            PublicServiceAppTheme(
                darkTheme = isDarkMode,
                language = currentLanguage
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
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Home, 
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        ) 
                    },
                    label = { Text(if (currentLanguage == "ur") "ہوم" else "Home") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Gray
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
                    label = { Text(if (currentLanguage == "ur") "پسندیدہ" else "Favorites") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Gray
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
                    label = { Text(if (currentLanguage == "ur") "معلومات" else "About") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Gray
                    )
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> HomeScreen(
                viewModel = homeViewModel,
                modifier = Modifier.padding(paddingValues)
            )
            1 -> FavoritesScreen(
                viewModel = favoritesViewModel,
                currentLanguage = currentLanguage,
                modifier = Modifier.padding(paddingValues)
            )
            2 -> AboutScreen(
                currentLanguage = currentLanguage,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
