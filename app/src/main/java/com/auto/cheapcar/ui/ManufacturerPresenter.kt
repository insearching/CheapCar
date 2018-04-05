package com.auto.cheapcar.ui

import android.util.Log
import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.api.CarsApi
import com.auto.cheapcar.entity.dto.Manufacturer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ManufacturerPresenter @Inject constructor(private val carsApi: CarsApi) :
        BasePresenter<ManufacturerPresenter.View> {

    override lateinit var view: View
    private lateinit var disposable: Disposable
    private var page = 0
    private var manufacturers: Manufacturer? = null

    override fun bind(view: View) {
        this.view = view
        getManufacturers()
    }

    override fun unbind() {
        disposable.dispose()
    }

    private fun getManufacturers() {
        disposable = carsApi.getManufacturers(page, CheapCarApplication.PAGE_SIZE,
                CheapCarApplication.WA_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ manufacturers ->
                    updateManufacturer(manufacturers)
                    Log.d("Manufacturers", manufacturers.titles.toString())
                }, { error ->
                    Log.e("Manufacturers", "Error", error)
                })
    }


    private fun updateManufacturer(manufacturers: Manufacturer){
        this.manufacturers = manufacturers

        view.updateManufacturerList()
    }

    interface View : PresentableView<ManufacturerPresenter> {
        fun updateManufacturerList(data: List<String>)
    }
}
