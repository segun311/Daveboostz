package com.daveboostz.app.performance

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class StandardPerformanceEngine(private val context: Context) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val _stats = MutableStateFlow(DeviceStats())
    val stats: StateFlow<DeviceStats> = _stats.asStateFlow()
    private var job: Job? = null

    fun start(intervalMs: Long = 3000L) {
        if (job?.isActive == true) return
        job = scope.launch {
            while (isActive) { _stats.value = collect(); delay(intervalMs) }
        }
    }

    fun stop() { job?.cancel(); job = null }

    private fun collect(): DeviceStats {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mem = ActivityManager.MemoryInfo().also { am.getMemoryInfo(it) }
        val ramPct = (((mem.totalMem - mem.availMem).toFloat() / mem.totalMem) * 100).toInt()
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val temp = (intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0) / 10f
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) ?: 0
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        return DeviceStats(
            batteryPercent = level,
            batteryTempCelsius = temp,
            ramUsedPercent = ramPct,
            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL
        )
    }

    data class DeviceStats(
        val batteryPercent: Int = 0,
        val batteryTempCelsius: Float = 0f,
        val ramUsedPercent: Int = 0,
        val isCharging: Boolean = false
    )
}
