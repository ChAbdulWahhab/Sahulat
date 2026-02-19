package com.publicservices.pakistan

import android.content.Intent
import android.os.Build
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.publicservices.pakistan.data.local.ServiceDatabase
import com.publicservices.pakistan.data.repository.ServiceRepository
import com.publicservices.pakistan.ui.about.AboutScreen
import com.publicservices.pakistan.ui.components.NotificationPermissionDialog
import com.publicservices.pakistan.ui.favorites.FavoritesScreen
import com.publicservices.pakistan.ui.favorites.FavoritesViewModel
import com.publicservices.pakistan.ui.home.HomeScreen
import com.publicservices.pakistan.ui.home.HomeViewModel
import com.publicservices.pakistan.ui.theme.Gray
import com.publicservices.pakistan.ui.theme.PublicServiceAppTheme
import com.publicservices.pakistan.utils.NotificationHelper
import com.publicservices.pakistan.utils.NotificationScheduler
import com.publicservices.pakistan.utils.PreferencesManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize notification channel
        NotificationHelper.createNotificationChannel(applicationContext)
        
        // Initialize database and repository
        val database = ServiceDatabase.getDatabase(applicationContext)
        val repository = ServiceRepository(database.serviceDao())
        
        setContent {
            val context = LocalContext.current
            val homeViewModel: HomeViewModel = viewModel(
                factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return HomeViewModel(repository, context) as T
                    }
                }
            )
            val isDarkMode by homeViewModel.isDarkMode.collectAsState()
            val currentLanguage by homeViewModel.currentLanguage.collectAsState()
            
            // Check if notification dialog should be shown
            var showNotificationDialog by remember {
                mutableStateOf(
                    !PreferencesManager.hasShownNotificationDialog(context)
                )
            }

            PublicServiceAppTheme(
                darkTheme = isDarkMode,
                language = currentLanguage
            ) {
                MainApp(
                    repository = repository,
                    homeViewModel = homeViewModel,
                    showNotificationDialog = showNotificationDialog,
                    onNotificationDialogDismiss = {
                        showNotificationDialog = false
                        PreferencesManager.setNotificationDialogShown(context, true)
                    },
                    onNotificationPermissionGranted = {
                        PreferencesManager.setNotificationsEnabled(context, true)
                        NotificationScheduler.scheduleDailyNotifications(context, currentLanguage)
                    }
                )
                
                // Show notification permission dialog
                if (showNotificationDialog) {
                    NotificationPermissionDialog(
                        language = currentLanguage,
                        onDismiss = {
                            showNotificationDialog = false
                            PreferencesManager.setNotificationDialogShown(context, true)
                        },
                        onPermissionGranted = {
                            PreferencesManager.setNotificationsEnabled(context, true)
                            NotificationScheduler.scheduleDailyNotifications(context, currentLanguage)
                        }
                    )
                }
            }
        }
        
        // Handle deep link from notification
        handleNotificationIntent(intent)
    }
    
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }
    
    private fun handleNotificationIntent(intent: Intent?) {
        val serviceId = intent?.getIntExtra("service_id", -1)
        if (serviceId != null && serviceId != -1) {
            // Navigate to specific service - will be handled in HomeScreen
            // For now, just store it in preferences or pass via ViewModel
        }
    }
}

@Composable
fun MainApp(
    repository: ServiceRepository,
    homeViewModel: HomeViewModel,
    showNotificationDialog: Boolean = false,
    onNotificationDialogDismiss: () -> Unit = {},
    onNotificationPermissionGranted: () -> Unit = {}
) {
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
