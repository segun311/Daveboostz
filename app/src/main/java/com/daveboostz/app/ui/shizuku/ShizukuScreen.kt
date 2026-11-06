package com.daveboostz.app.ui.shizuku

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.daveboostz.app.shizuku.ShizukuState
import com.daveboostz.app.ui.components.NeonButton
import com.daveboostz.app.ui.dashboard.DashboardViewModel
import com.daveboostz.app.ui.theme.*

@Composable
fun ShizukuScreen(vm: DashboardViewModel = hiltViewModel()) {
    val state by vm.shizukuState.collectAsState()
    Column(Modifier.fillMaxSize().background(PureBlack).verticalScroll(rememberScrollState()).padding(16.dp)) {
        Text("SHIZUKU CONTROL", color = NeonRed, fontWeight = FontWeight.Black, fontSize = 20.sp, letterSpacing = 3.sp)
        Spacer(Modifier.height(16.dp))
        Text("STATE", color = TextSecondary, fontSize = 10.sp, letterSpacing = 2.sp)
        val (label, color) = when (state) {
            ShizukuState.Ready -> "CONNECTED" to GreenAccent
            ShizukuState.NotInstalled -> "NOT INSTALLED" to NeonRed
            ShizukuState.NotRunning -> "NOT RUNNING" to NeonRed
            ShizukuState.PermissionRequired -> "WAITING FOR AUTHORIZATION" to CyanAccent
            ShizukuState.Unsupported -> "UNSUPPORTED VERSION" to NeonRed
            is ShizukuState.Error -> "ERROR: ${state.message}" to NeonRed
        }
        Text(label, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(Modifier.height(24.dp))
        NeonButton("REQUEST PERMISSION", enabled = state == ShizukuState.PermissionRequired) { vm.requestShizukuPermission() }
        Spacer(Modifier.height(8.dp))
        NeonButton("RECONNECT") { vm.refreshShizuku() }
    }
}
