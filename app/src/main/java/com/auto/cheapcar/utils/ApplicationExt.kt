package com.auto.cheapcar.utils

import android.app.Application
import com.auto.cheapcar.BuildConfig
import com.auto.cheapcar.di.ComponentProvider
import com.auto.cheapcar.di.component.ApplicationComponent
import com.auto.cheapcar.di.component.DaggerApplicationComponent
import com.auto.cheapcar.di.module.android.ContextModule

fun Application.dependencies(): ApplicationComponent {
    val applicationComponent = DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this, BuildConfig.SIMULATION))
            .build()
    ComponentProvider.initialize(applicationComponent)
    return applicationComponent
}
