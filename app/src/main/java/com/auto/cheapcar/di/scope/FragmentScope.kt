package com.auto.cheapcar.di.scope

import javax.inject.Scope

/**
 * The [FragmentScope] annotation for DI Dagger (injecting into activities), indicates the
 * Fragment scope.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class FragmentScope