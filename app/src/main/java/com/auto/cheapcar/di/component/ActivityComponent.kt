package com.auto.cheapcar.di.component

import com.auto.cheapcar.di.scope.ActivityScope
import com.auto.cheapcar.ui.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}