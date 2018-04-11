package com.auto.cheapcar.utils

import android.content.pm.PackageManager
import android.graphics.Typeface
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.auto.cheapcar.R
import com.auto.cheapcar.di.ComponentProvider
import com.auto.cheapcar.di.component.DaggerActivityComponent


fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment) {
    supportFragmentManager.transact {
        replace(R.id.content, fragment)
        addToBackStack(fragment::class.toString())
    }
}

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

fun Snackbar.setTextColor(color: Int): Snackbar {
    val tv = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
    tv.setTextColor(color)
    val face = Typeface.create("sans-serif-medium", Typeface.NORMAL)
    tv.typeface = face
    return this
}

fun Snackbar.setTypeface(fontFamily: String): Snackbar {
    val tv = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
    val face = Typeface.create(fontFamily, Typeface.NORMAL)
    tv.typeface = face
    return this
}