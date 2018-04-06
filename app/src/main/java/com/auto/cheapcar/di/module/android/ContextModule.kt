package com.auto.cheapcar.di.module.android

import android.content.Context
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
open class ContextModule(val context: Context) {

    @ApplicationScope
    @Provides
    fun context(): Context = context.applicationContext
}