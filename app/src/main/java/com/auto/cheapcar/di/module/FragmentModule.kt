package com.auto.cheapcar.di.module

import android.support.v4.app.Fragment
import dagger.Module
import dagger.Provides

/**
 * The [FragmentModule] provides dependencies for the
 * [android.support.v4.app.Fragment]s.
 */
@Module
class FragmentModule
/**
 * Instantiates the [FragmentModule] object.
 *
 * @param fragment the client fragment
 */
(private val fragment: Fragment) {

    @Provides
    protected fun provideFragment(): Fragment {
        return fragment
    }
}