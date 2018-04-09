package com.auto.cheapcar.utils

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.auto.cheapcar.R
import com.auto.cheapcar.di.ComponentProvider
import com.auto.cheapcar.di.component.DaggerFragmentComponent
import com.auto.cheapcar.di.component.FragmentComponent

fun Fragment.replaceFragment(fragment: Fragment) {
    activity!!.supportFragmentManager.transact {
        setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        replace(R.id.content, fragment, fragment.javaClass.toString()).addToBackStack(fragment.javaClass.toString())
        addToBackStack(fragment::class.toString())
    }
}
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun Fragment.toast(message: String) {
    Toast.makeText(activity!!.baseContext, message, Toast.LENGTH_LONG).show()
}

fun Fragment.string(@StringRes id: Int): String = resources.getString(id)

fun Fragment.dependencies(): FragmentComponent =
        DaggerFragmentComponent
                .builder()
                .applicationComponent(ComponentProvider.getApplicationComponent())
                .build()
