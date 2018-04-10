package com.auto.cheapcar.ui

import android.content.SharedPreferences
import android.util.Log
import com.auto.cheapcar.CarRepository
import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.R
import com.auto.cheapcar.entity.bo.Brand
import com.auto.cheapcar.utils.manager.InternetManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ManufacturerPresenter @Inject constructor(private val carsRepository: CarRepository,
                                                private val prefs: SharedPreferences,
                                                private val internetManager: InternetManager) :
        BasePresenter<ManufacturerPresenter.View> {

    override lateinit var view: View
    private lateinit var disposable: Disposable
    private var brands: List<Brand> = emptyList()

    override fun bind(view: View) {
        this.view = view
        loadManufacturers()
    }

    override fun unbind() {
        disposable.dispose()
    }

    fun onBindRowViewAtPosition(position: Int, rowView: ManufacturerFragment.RowView) {
        rowView.setTitle(brands[position].title)
        rowView.setBackground(if (position % 2 == 0) R.drawable.list_item_clicked_light else R
                .drawable.list_item_clicked_dark)
        rowView.setOnItemClickListener(android.view.View.OnClickListener {
            view.selectManufacturer(brands[position])
        })
    }

    fun getManufacturersCount() = brands.size

    fun checkForMoreItems(visibleItemCount: Int, totalItemCount: Int,
                                   firstVisibleItemPosition: Int) {
        val pageCount = prefs.getInt(CarRepository.MANUFACTURER_PAGE_COUNT, 0)
        if (!carsRepository.isLoading && pagesLoaded() < pageCount) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= CheapCarApplication.PAGE_SIZE) {
                loadManufacturers()
            }
        }
    }

    private fun pagesLoaded() = prefs.getInt(CarRepository.MANUFACTURER_PAGE_LOADED, 0)

    private fun loadManufacturers() {
        disposable = carsRepository.getManufacturers(pagesLoaded(), CheapCarApplication.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ if (!internetManager.connectionAvailable()) view.showNoInternetMessage() })
                .doOnSubscribe({ view.showLoading(true) })
                .doOnNext({ view.showLoading(false) })
                .doOnError({ view.showLoading(false) })
                .subscribe({ brands ->
                    updateManufacturer(brands)
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

        fun showNoInternetMessage()

        fun selectManufacturer(brand: Brand)

        fun showLoading(show: Boolean)
    }
}
