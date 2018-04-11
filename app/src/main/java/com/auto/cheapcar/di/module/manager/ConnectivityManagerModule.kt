package com.auto.cheapcar.di.module.manager

import android.content.Context
import android.net.ConnectivityManager
import com.auto.cheapcar.di.module.android.ContextModule
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(ContextModule::class))
class ConnectivityManagerModule {

    @ApplicationScope
    @Provides
    fun connectivityManager(context: Context) = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}