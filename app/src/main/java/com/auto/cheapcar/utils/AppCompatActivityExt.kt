package com.auto.cheapcar.utils

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.auto.cheapcar.R
import com.auto.cheapcar.di.ComponentProvider
import com.auto.cheapcar.di.component.DaggerActivityComponent


fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment) {
    supportFragmentManager.transact {
        replace(R.id.content, fragment)
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