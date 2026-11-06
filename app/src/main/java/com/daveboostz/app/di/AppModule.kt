package com.daveboostz.app.di

import android.content.Context
import com.daveboostz.app.performance.StandardPerformanceEngine
import com.daveboostz.app.shizuku.AdvancedSystemEngine
import com.daveboostz.app.shizuku.ShizukuManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun shizuku(@ApplicationContext ctx: Context) = ShizukuManager(ctx)

    @Provides @Singleton
    fun advanced(shizuku: ShizukuManager) = AdvancedSystemEngine(shizuku)

    @Provides @Singleton
    fun perf(@ApplicationContext ctx: Context) = StandardPerformanceEngine(ctx)
}
