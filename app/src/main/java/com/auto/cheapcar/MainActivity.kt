package com.auto.cheapcar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.auto.cheapcar.api.CarsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        carsApi.getManufacturers(0, CheapCarApplication.PAGE_SIZE, CheapCarApplication.WA_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ manufacturers ->
                    Log.d("Manufacturers", manufacturers.names.toString())
                }, { error ->
                    Log.e("Manufacturers", "Error", error)
                })

        carsApi.getMainTypes(107, 0, CheapCarApplication.PAGE_SIZE, CheapCarApplication.WA_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ mainTypes ->
                    Log.d("Main types", mainTypes.toString())
                }, { error ->
                    Log.e("Main types", "Error", error)
                })

        carsApi.getBuildDates(107, "Arnage", CheapCarApplication.WA_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ buildDates ->
                    Log.d("Build dates", buildDates.toString())
                }, { error ->
                    Log.e("Build dates", "Error", error)
                })
    }

    private val carsApi by lazy {
        CarsApi.create()
    }
}
