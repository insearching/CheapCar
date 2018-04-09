package com.auto.cheapcar.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.auto.cheapcar.database.AppDatabase
import com.auto.cheapcar.di.module.android.ContextModule
import com.auto.cheapcar.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(ContextModule::class))
class DatabaseModule {

    @Provides
    @ApplicationScope
    fun provideDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "cheepcar").build()
}