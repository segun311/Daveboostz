package com.daveboostz.app.shizuku

import rikka.shizuku.Shizuku

class AdvancedSystemEngine(private val shizukuManager: ShizukuManager) {

    fun executeShellCommand(command: String): ShellResult {
        if (shizukuManager.state.value != ShizukuState.Ready) {
            return ShellResult(false, "", "Shizuku not ready")
        }
        return runCatching {
            val process = Shizuku.newProcess(arrayOf("sh", "-c", command), null, null)
            val stdout = process.inputStream.bufferedReader().readText()
            val stderr = process.errorStream.bufferedReader().readText()
            val code = process.waitFor()
            ShellResult(code == 0, stdout, stderr)
        }.getOrElse { ShellResult(false, "", it.message ?: "Execution failed") }
    }

    fun forceStopApp(pkg: String): ShellResult {
        val blocked = pkg == "android" || pkg.startsWith("com.android.systemui") || pkg == "com.google.android.gms"
        if (blocked) return ShellResult(false, "", "Blocked system-critical package")
        return executeShellCommand("am force-stop $pkg")
    }

    fun setAnimationScale(scale: Float): ShellResult {
        val s = scale.toString()
        return executeShellCommand(
            "settings put global window_animation_scale $s; " +
            "settings put global transition_animation_scale $s; " +
            "settings put global animator_duration_scale $s"
        )
    }

    fun setRefreshRate(hz: Float): ShellResult =
        executeShellCommand("settings put system peak_refresh_rate $hz")

    data class ShellResult(val success: Boolean, val stdout: String, val stderr: String)
}
