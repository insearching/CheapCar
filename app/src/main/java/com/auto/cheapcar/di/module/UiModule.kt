package com.auto.cheapcar.di.module

import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.di.scope.UiScope
import dagger.Module
import dagger.Provides

/**
 * Dagger module for providing application-level (UI layer) dependencies.
 */
@Module
class UiModule
/**
 * Instantiates the [UiModule]
 *
 * @param application the [CheapCarApplication]
 */
(private val application: CheapCarApplication) {

    /**
     * Provides the [CheapCarApplication].
     *
     * @return the [CheapCarApplication]
     */
    @Provides
    @UiScope
    internal fun provideBleConnectApplication(): CheapCarApplication {
        return application
    }
    
}
