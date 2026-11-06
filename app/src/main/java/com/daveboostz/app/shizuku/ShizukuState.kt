package com.daveboostz.app.shizuku

sealed interface ShizukuState {
    data object NotInstalled : ShizukuState
    data object NotRunning : ShizukuState
    data object PermissionRequired : ShizukuState
    data object Ready : ShizukuState
    data object Unsupported : ShizukuState
    data class Error(val message: String) : ShizukuState
}
