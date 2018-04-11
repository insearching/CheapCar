package com.auto.cheapcar.di

import com.auto.cheapcar.di.component.ApplicationComponent


object ComponentProvider {

    @Volatile private lateinit var applicationComponent: ApplicationComponent
    @Volatile private var initialized: Boolean = false

    fun initialize(applicationComponent: ApplicationComponent) {
        initialized = true
        this.applicationComponent = applicationComponent
    }


    fun getApplicationComponent(): ApplicationComponent {
        if (!initialized) {
            throw IllegalStateException("ComponentProvider.initialize() was not called")
        }
        return applicationComponent
    }

}