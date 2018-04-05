package com.auto.cheapcar.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.support.annotation.IdRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.auto.cheapcar.R
import com.auto.cheapcar.di.ComponentProvider
import com.auto.cheapcar.di.component.DaggerActivityComponent


/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment) {
    supportFragmentManager.transact {
        replace(R.id.content, fragment)
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
    supportFragmentManager.transact {
        add(fragment, tag)
    }
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
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

fun AppCompatActivity.dependencies() =
        DaggerActivityComponent.builder()
                .applicationComponent(ComponentProvider.getApplicationComponent())
                .build()!!


fun AppCompatActivity.requestPermissions(vararg permissions: String) {
    val list = permissions.filter { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }
    if (list.isEmpty()) {
        return
    }
    ActivityCompat.requestPermissions(this, list.toTypedArray(), 1001)
}

fun AppCompatActivity.hideKeyboard(){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    try {
        if (window.decorView.findFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
        } else {
            inputMethodManager.hideSoftInputFromWindow(null, 0)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

}