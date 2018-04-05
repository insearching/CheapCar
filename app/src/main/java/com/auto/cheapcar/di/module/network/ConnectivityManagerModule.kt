package com.auto.cheapcar.di.module.network

import android.content.Context
import android.net.ConnectivityManager
import com.auto.cheapcar.di.scope.ApplicationScope
import com.liftbrands.di.module.android.ContextModule
import dagger.Module
import dagger.Provides

@Module(includes = [(ContextModule::class)])
class ConnectivityManagerModule {

    @ApplicationScope
    @Provides
    fun connectivityManager(context: Context) = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}