package com.auto.cheapcar.di.component


import com.auto.cheapcar.di.scope.FragmentScope
import com.auto.cheapcar.ui.BuildDateFragment
import com.auto.cheapcar.ui.MainTypeFragment
import com.auto.cheapcar.ui.ManufacturerFragment
import dagger.Component

@FragmentScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface FragmentComponent {
    fun inject(manufacturerFragment: ManufacturerFragment)
    fun inject(mainTypeFragment: MainTypeFragment)
    fun inject(buildDateFragment: BuildDateFragment)
}