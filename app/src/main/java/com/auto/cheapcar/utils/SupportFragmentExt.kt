package com.auto.cheapcar.utils

import android.app.Activity
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.auto.cheapcar.R
import com.auto.cheapcar.di.ComponentProvider
import com.auto.cheapcar.di.component.DaggerFragmentComponent
import com.auto.cheapcar.di.component.FragmentComponent




/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun Fragment.replaceFragment(fragment: Fragment) {
    hideKeyboard()
    activity!!.supportFragmentManager.transact {
        replace(R.id.content, fragment, fragment.javaClass.toString()).addToBackStack(fragment.javaClass.toString())
    }
}

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun Fragment.replaceFragmentWithPopBackStack(fragment: Fragment) {
    hideKeyboard()
    activity!!.supportFragmentManager.transact {
        replace(R.id.content, fragment)
        activity!!.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

fun Fragment.replaceSecondFragmentInTabletLandscape(@IdRes secondFragmentPlaceholder: Int, fragment: Fragment) {
    hideKeyboard()
    childFragmentManager.transact {
        replace(secondFragmentPlaceholder, fragment)
        childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun Fragment.toast(message: String) {
    Toast.makeText(activity!!.baseContext, message, Toast.LENGTH_LONG).show()
}

fun Fragment.string(@StringRes id: Int): String = resources.getString(id)

@ColorInt
fun Fragment.getThemeAttrColor(@AttrRes colorAttr: Int): Int {
    val array = context!!.obtainStyledAttributes(null, intArrayOf(colorAttr))
    try {
        return array.getColor(0, 0)
    } finally {
        array.recycle()
    }
}

fun Fragment.hideKeyboard() {
    val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    try {
        if (activity!!.window.decorView.findFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity!!.window.decorView.windowToken, 0)
        } else {
            inputMethodManager.hideSoftInputFromWindow(null, 0)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Fragment.dependencies(): FragmentComponent =
        DaggerFragmentComponent
                .builder()
                .applicationComponent(ComponentProvider.getApplicationComponent())
                .build()
