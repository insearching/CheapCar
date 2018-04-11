package com.auto.cheapcar.di.module.android

import android.content.Context
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
open class ContextModule(val context: Context, private val simulation: Boolean) {

    @ApplicationScope
    @Provides
    fun context(): Context = context.applicationContext

    @Provides
    @ApplicationScope
    @Named("simulation")
    fun isSimulation(): Boolean = simulation
}