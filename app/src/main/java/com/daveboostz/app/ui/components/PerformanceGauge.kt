package com.daveboostz.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daveboostz.app.ui.theme.GridLine
import com.daveboostz.app.ui.theme.NeonRed
import com.daveboostz.app.ui.theme.NeonRedDim
import com.daveboostz.app.ui.theme.TextSecondary
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PerformanceGauge(score: Int, label: String, modifier: Modifier = Modifier) {
    val p by animateFloatAsState(score / 100f, tween(1200), label = "g")
    Box(modifier, contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val sw = 14.dp.toPx()
            val r = (size.minDimension - sw) / 2
            val c = Offset(size.width / 2, size.height / 2)
            for (i in 0 until 60) {
                val a = (i * 6f) * (Math.PI / 180).toFloat()
                val len = if (i % 5 == 0) 12.dp.toPx() else 6.dp.toPx()
                val s = r + 10.dp.toPx()
                drawLine(
                    color = NeonRedDim.copy(alpha = 0.5f),
                    start = Offset(c.x + s * cos(a), c.y + s * sin(a)),
                    end = Offset(c.x + (s + len) * cos(a), c.y + (s + len) * sin(a)),
                    strokeWidth = 2.dp.toPx()
                )
            }
            drawArc(color = GridLine, startAngle = 135f, sweepAngle = 270f, useCenter = false,
                topLeft = Offset(c.x - r, c.y - r), size = Size(r * 2, r * 2),
                style = Stroke(sw, cap = StrokeCap.Round))
            drawArc(brush = Brush.sweepGradient(listOf(NeonRedDim, NeonRed, NeonRed)),
                startAngle = 135f, sweepAngle = 270f * p, useCenter = false,
                topLeft = Offset(c.x - r, c.y - r), size = Size(r * 2, r * 2),
                style = Stroke(sw, cap = StrokeCap.Round))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$score", color = NeonRed, fontSize = 52.sp, fontWeight = FontWeight.Black)
            Text(label.uppercase(), color = TextSecondary, fontSize = 10.sp)
        }
    }
}
