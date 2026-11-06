package com.daveboostz.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.daveboostz.app.navigation.DaveBoostzNav
import com.daveboostz.app.ui.theme.ThemeDaveBoostz
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { ThemeDaveBoostz { DaveBoostzNav() } }
    }
}
