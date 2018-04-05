package com.auto.cheapcar.ui

import android.Manifest
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.auto.cheapcar.R
import com.auto.cheapcar.utils.hideKeyboard
import com.auto.cheapcar.utils.replaceFragmentInActivity
import com.auto.cheapcar.utils.requestPermissions

class MainActivity : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)

//        carsApi.getMainTypes(107, 0, CheapCarApplication.PAGE_SIZE, CheapCarApplication.WA_KEY)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ mainTypes ->
//                    Log.d("Main types", mainTypes.toString())
//                }, { error ->
//                    Log.e("Main types", "Error", error)
//                })
//
//        carsApi.getBuildDates(107, "Arnage", CheapCarApplication.WA_KEY)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ buildDates ->
//                    Log.d("Build dates", buildDates.toString())
//                }, { error ->
//                    Log.e("Build dates", "Error", error)
//                })
    }

    override fun onBackPressed() {
        hideKeyboard()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    fun switchToManufacturerScreen() {
        (supportFragmentManager.findFragmentById(R.id.content) as ManufacturerFragment?
                ?: ManufacturerFragment.newInstance()).also {
            replaceFragmentInActivity(it)
        }
    }
}
