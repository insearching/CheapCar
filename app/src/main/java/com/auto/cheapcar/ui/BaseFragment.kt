package com.auto.cheapcar.ui


import android.support.v4.app.Fragment
import com.auto.cheapcar.di.ComponentProvider
import com.liftbrands.di.component.FragmentComponent

open class BaseFragment : Fragment() {

    fun dependencies(): FragmentComponent =
            DaggerFragmentComponent
                    .builder()
                    .applicationComponent(ComponentProvider.getApplicationComponent())
                    .build()
}