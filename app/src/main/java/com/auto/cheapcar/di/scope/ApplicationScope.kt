package com.auto.cheapcar.di.scope

import javax.inject.Scope

/**
 * The [ApplicationScope] annotation for DI Dagger (injecting into activities), indicates the
 * Application scope.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ApplicationScope