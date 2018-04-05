package com.liftbrands.di.module.android

import android.content.Context
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = [(ContextModule::class)])
class SharedPreferenceModule {

    @ApplicationScope
    @Provides
    fun sharedPreferences(context: Context) = context.getSharedPreferences("personal", Context.MODE_PRIVATE)!!
}