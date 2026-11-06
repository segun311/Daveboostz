package com.daveboostz.app.overlays

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.provider.Settings
import android.util.TypedValue
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.daveboostz.app.performance.StandardPerformanceEngine
import kotlinx.coroutines.*

class HudOverlayService : Service() {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var view: LinearLayout? = null
    private var engine: StandardPerformanceEngine? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        if (!Settings.canDrawOverlays(this)) { stopSelf(); return }
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.argb(200, 0, 0, 0))
            val p = (8 * resources.displayMetrics.density).toInt()
            setPadding(p, p, p, p)
        }
        val title = tv("DAVEBOOSTZ HUD", Color.rgb(255, 23, 68))
        val bat = tv("BAT --", Color.WHITE)
        val temp = tv("TEMP --", Color.WHITE)
        val ram = tv("RAM --", Color.WHITE)
        val hz = tv("DISPLAY --Hz", Color.rgb(0, 229, 255))
        layout.addView(title); layout.addView(bat); layout.addView(temp); layout.addView(ram); layout.addView(hz)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply { gravity = Gravity.TOP or Gravity.START; x = 80; y = 120 }

        wm.addView(layout, params)
        view = layout

        engine = StandardPerformanceEngine(this).also { it.start(1500) }
        scope.launch {
            engine!!.stats.collect { s ->
                bat.text = "BAT ${s.batteryPercent}%"
                temp.text = "TEMP ${s.batteryTempCelsius} C"
                ram.text = "RAM ${s.ramUsedPercent}%"
                val r = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.refreshRate
                hz.text = "DISPLAY ${"%.0f".format(r)}Hz"
            }
        }
    }

    private fun tv(t: String, c: Int): TextView = TextView(this).apply {
        text = t; setTextColor(c)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
    }

    override fun onDestroy() {
        scope.cancel()
        engine?.stop()
        view?.let { v ->
            runCatching {
                (getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(v)
            }
        }
        super.onDestroy()
    }
}
