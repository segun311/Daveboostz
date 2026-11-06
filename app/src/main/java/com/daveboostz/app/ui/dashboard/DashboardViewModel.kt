package com.daveboostz.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daveboostz.app.performance.StandardPerformanceEngine
import com.daveboostz.app.shizuku.AdvancedSystemEngine
import com.daveboostz.app.shizuku.ShizukuManager
import com.daveboostz.app.shizuku.ShizukuState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    perf: StandardPerformanceEngine,
    private val shizuku: ShizukuManager,
    private val advanced: AdvancedSystemEngine
) : ViewModel() {
    val stats = perf.stats
    val shizukuState = shizuku.state

    private val _boostLog = MutableStateFlow<List<String>>(emptyList())
    val boostLog: StateFlow<List<String>> = _boostLog.asStateFlow()

    init { perf.start(2500) }

    fun performanceScore(): Int {
        val s = stats.value
        var score = 100
        if (s.batteryPercent < 20) score -= 20
        if (s.batteryTempCelsius > 40f) score -= 15
        if (s.ramUsedPercent > 85) score -= 15
        return score.coerceIn(0, 100)
    }

    fun runBoost() {
        viewModelScope.launch {
            val log = mutableListOf<String>()
            log += "Standard engine: monitoring started"
            if (shizuku.state.value == ShizukuState.Ready) {
                val anim = advanced.setAnimationScale(0.5f)
                log += if (anim.success) "Shizuku: animation scale set to 0.5x" else "Shizuku: animation scale failed"
            } else {
                log += "Shizuku not connected - advanced ops skipped"
            }
            _boostLog.value = log
        }
    }

    fun requestShizukuPermission() = shizuku.requestPermission()
    fun refreshShizuku() = shizuku.refresh()
}
