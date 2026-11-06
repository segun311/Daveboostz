package com.daveboostz.app.ui.settings

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daveboostz.app.ui.components.NeonButton
import com.daveboostz.app.ui.theme.*

@Composable
fun SettingsScreen() {
    val ctx = LocalContext.current
    Column(Modifier.fillMaxSize().background(PureBlack).padding(16.dp)) {
        Text("SETTINGS", color = NeonRed, fontWeight = FontWeight.Black, fontSize = 20.sp, letterSpacing = 3.sp)
        Spacer(Modifier.height(16.dp))
        NeonButton("OVERLAY PERMISSION") {
            ctx.startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${ctx.packageName}")))
        }
        Spacer(Modifier.height(8.dp))
        NeonButton("USAGE ACCESS") {
            ctx.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
        Spacer(Modifier.height(8.dp))
        NeonButton("NOTIFICATION POLICY") {
            ctx.startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
        }
        Spacer(Modifier.height(8.dp))
        NeonButton("OPEN SHIZUKU APP") {
            runCatching { ctx.packageManager.getLaunchIntentForPackage("moe.shizuku.privileged.api") }
                .getOrNull()?.let { ctx.startActivity(it) }
        }
    }
}
