package com.auto.cheapcar.ui

import android.Manifest
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.auto.cheapcar.R
import com.auto.cheapcar.utils.dependencies
import com.auto.cheapcar.utils.hideKeyboard
import com.auto.cheapcar.utils.replaceFragmentInActivity
import com.auto.cheapcar.utils.requestPermissions
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainPresenter.View{

    @Inject
    override lateinit var presenter: MainPresenter

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        dependencies().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onBackPressed() {
        hideKeyboard()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun switchToManufacturerScreen() {
        (supportFragmentManager.findFragmentById(R.id.content) as ManufacturerFragment?
                ?: ManufacturerFragment.newInstance()).also {
            replaceFragmentInActivity(it)
        }
    }
}
