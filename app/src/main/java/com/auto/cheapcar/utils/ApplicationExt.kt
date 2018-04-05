package com.auto.cheapcar.utils

import android.app.Application
import com.auto.cheapcar.di.ComponentProvider
import com.liftbrands.di.ComponentProvider
import com.liftbrands.di.component.ApplicationComponent
import com.liftbrands.di.component.DaggerApplicationComponent
import com.liftbrands.di.module.android.ContextModule

fun Application.injectDependencies(): ApplicationComponent {
    val applicationComponent = DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    ComponentProvider.initialize(applicationComponent)
    return applicationComponent
}
