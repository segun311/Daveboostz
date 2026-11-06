package com.daveboostz.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.daveboostz.app.shizuku.ShizukuState
import com.daveboostz.app.ui.components.NeonButton
import com.daveboostz.app.ui.components.PerformanceGauge
import com.daveboostz.app.ui.components.StatusChip
import com.daveboostz.app.ui.theme.*

@Composable
fun DashboardScreen(vm: DashboardViewModel = hiltViewModel(), onNav: (String) -> Unit) {
    val stats by vm.stats.collectAsState()
    val shizuku by vm.shizukuState.collectAsState()
    val log by vm.boostLog.collectAsState()
    val scroll = rememberScrollState()

    Column(Modifier.fillMaxSize().background(PureBlack).verticalScroll(scroll).padding(16.dp)) {
        Text("DAVEBOOSTZ", color = NeonRed, fontWeight = FontWeight.Black, fontSize = 18.sp, letterSpacing = 3.sp)
        Spacer(Modifier.height(4.dp))
        Text("UNLEASH YOUR DEVICE. DOMINATE YOUR GAME.", color = TextSecondary, fontSize = 8.sp, letterSpacing = 2.sp)
        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            StatusChip("BAT ${stats.batteryPercent}%")
            StatusChip("TEMP ${stats.batteryTempCelsius} C")
            StatusChip("RAM ${stats.ramUsedPercent}%")
            val shText = when (shizuku) {
                ShizukuState.Ready -> "SHIZUKU OK"
                ShizukuState.NotInstalled -> "NO SHIZUKU"
                ShizukuState.NotRunning -> "OFFLINE"
                ShizukuState.PermissionRequired -> "PERM REQ"
                else -> "ERROR"
            }
            StatusChip(shText, if (shizuku == ShizukuState.Ready) GreenAccent else NeonRed)
        }

        Spacer(Modifier.height(24.dp))
        PerformanceGauge(vm.performanceScore(), "PERFORMANCE",
            Modifier.size(260.dp).align(Alignment.CenterHorizontally))

        Spacer(Modifier.height(24.dp))
        NavRow("GAME LIBRARY") { onNav("library") }
        NavRow("SHIZUKU CONTROL") { onNav("shizuku") }
        NavRow("SETTINGS") { onNav("settings") }

        Spacer(Modifier.height(24.dp))
        NeonButton("BOOST NOW") { vm.runBoost() }

        if (log.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Text("BOOST RESULT", color = NeonRed, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            log.forEach { Text("- $it", color = TextPrimary, fontSize = 11.sp) }
        }
    }
}

@Composable
private fun NavRow(label: String, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(vertical = 4.dp).background(CardSurface).clickable { onClick() }.padding(16.dp)) {
        Text(label, color = TextPrimary, fontWeight = FontWeight.Medium, letterSpacing = 2.sp)
    }
}
