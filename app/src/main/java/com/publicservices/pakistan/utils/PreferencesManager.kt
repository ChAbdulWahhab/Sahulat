package com.publicservices.pakistan.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREFS_NAME = "sahulat_prefs"
    private const val KEY_NOTIFICATION_DIALOG_SHOWN = "notification_dialog_shown"
    private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
    private const val KEY_SEARCH_HISTORY = "search_history"
    
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    fun hasShownNotificationDialog(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_NOTIFICATION_DIALOG_SHOWN, false)
    }
    
    fun setNotificationDialogShown(context: Context, shown: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_NOTIFICATION_DIALOG_SHOWN, shown).apply()
    }
    
    fun areNotificationsEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_NOTIFICATIONS_ENABLED, false)
    }
    
    fun setNotificationsEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply()
    }
    
    fun getSearchHistory(context: Context): List<String> {
        val historyString = getPrefs(context).getString(KEY_SEARCH_HISTORY, "") ?: ""
        return if (historyString.isEmpty()) {
            emptyList()
        } else {
            historyString.split(",").filter { it.isNotBlank() }
        }
    }
    
    fun addToSearchHistory(context: Context, query: String) {
        if (query.isBlank()) return
        
        val currentHistory = getSearchHistory(context).toMutableList()
        // Remove if already exists
        currentHistory.remove(query)
        // Add to front
        currentHistory.add(0, query)
        // Keep only last 10 searches
        val limitedHistory = currentHistory.take(10)
        
        getPrefs(context).edit()
            .putString(KEY_SEARCH_HISTORY, limitedHistory.joinToString(","))
            .apply()
    }
    
    fun clearSearchHistory(context: Context) {
        getPrefs(context).edit().remove(KEY_SEARCH_HISTORY).apply()
    }
}
