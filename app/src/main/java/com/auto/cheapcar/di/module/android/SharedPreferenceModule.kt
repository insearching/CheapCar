package com.auto.cheapcar.di.module.android

import android.content.Context
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(ContextModule::class))
class SharedPreferenceModule {

    @Provides
    @ApplicationScope
    fun sharedPreferences(context: Context) = context.getSharedPreferences("personal", Context.MODE_PRIVATE)!!
}