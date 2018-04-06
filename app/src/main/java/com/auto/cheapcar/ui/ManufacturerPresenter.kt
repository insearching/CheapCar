package com.auto.cheapcar.ui

import android.util.Log
import com.auto.cheapcar.CarRepository
import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.entity.bo.Brand
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ManufacturerPresenter @Inject constructor(private val carsRepository: CarRepository) :
        BasePresenter<ManufacturerPresenter.View> {

    override lateinit var view: View
    private lateinit var disposable: Disposable
    private var page = 0
    private var brands: List<Brand> = emptyList()

    override fun bind(view: View) {
        this.view = view
        getManufacturers()
    }

    override fun unbind() {
        disposable.dispose()
    }

    internal fun onBindRowViewAtPosition(position: Int, rowView: ManufacturerFragment.RowView) {
        rowView.setTitle(brands[position].title)
    }

    internal fun getPersonsCount(): Int {
        return brands.size
    }

    private fun getManufacturers() {
        disposable = carsRepository.getManufacturers(page, CheapCarApplication.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ brands ->
                    updateManufacturer(brands)
                    Log.d("Manufacturers", brands.toString())
                }, { error ->
                    Log.e("Manufacturers", "Error", error)
                })
    }

    private fun updateManufacturer(brands: List<Brand>) {
        this.brands = brands
        view.updateManufacturerList()
    }

    interface View : PresentableView<ManufacturerPresenter> {
        fun updateManufacturerList()
    }
}
