package com.daveboostz.app.ui.gamelibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daveboostz.app.ui.theme.*

@Composable
fun GameLibraryScreen() {
    Column(Modifier.fillMaxSize().background(PureBlack).padding(16.dp)) {
        Text("GAME LIBRARY", color = NeonRed, fontWeight = FontWeight.Black, fontSize = 20.sp, letterSpacing = 3.sp)
        Spacer(Modifier.height(8.dp))
        Text("Coming in next build: automatic detection + manual add.",
            color = TextSecondary, fontSize = 11.sp)
    }
}
