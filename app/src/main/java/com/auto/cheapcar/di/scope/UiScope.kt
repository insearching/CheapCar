package com.auto.cheapcar.di.scope

import javax.inject.Scope

/**
 * The [UiScope] annotation for DI Dagger (injecting into activities), indicates the
 * Ui scope.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class UiScope