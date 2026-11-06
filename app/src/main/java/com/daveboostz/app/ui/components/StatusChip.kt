package com.daveboostz.app.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daveboostz.app.ui.theme.NeonRedDim
import com.daveboostz.app.ui.theme.TextPrimary

@Composable
fun StatusChip(text: String, color: Color = TextPrimary) {
    Text(text = text, color = color, fontSize = 9.sp,
        modifier = Modifier.border(1.dp, NeonRedDim).padding(horizontal = 6.dp, vertical = 3.dp))
}
