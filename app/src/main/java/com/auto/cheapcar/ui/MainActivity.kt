package com.auto.cheapcar.ui

import android.Manifest
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.auto.cheapcar.R
import com.auto.cheapcar.utils.dependencies
import com.auto.cheapcar.utils.replaceFragmentInActivity
import com.auto.cheapcar.utils.requestPermissions
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainPresenter.View {

    @Inject
    override lateinit var presenter: MainPresenter

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        dependencies().inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 1)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun switchToManufacturerScreen() {
        val fragment = supportFragmentManager.findFragmentById(R.id.content)
        if (fragment is ManufacturerFragment?) {
            (fragment ?: ManufacturerFragment.newInstance()).also {
                replaceFragmentInActivity(it)
            }
        }
    }
}
