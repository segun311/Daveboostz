package com.daveboostz.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daveboostz.app.ui.dashboard.DashboardScreen
import com.daveboostz.app.ui.gamelibrary.GameLibraryScreen
import com.daveboostz.app.ui.settings.SettingsScreen
import com.daveboostz.app.ui.shizuku.ShizukuScreen

@Composable
fun DaveBoostzNav() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen { r -> nav.navigate(r) } }
        composable("shizuku") { ShizukuScreen() }
        composable("settings") { SettingsScreen() }
        composable("library") { GameLibraryScreen() }
    }
}
