package com.auto.cheapcar.di.scope

import javax.inject.Scope

/**
 * The [ActivityScope] annotation for DI Dagger (injecting into activities), indicates the
 * Activity scope.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ActivityScope