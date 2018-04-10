package com.auto.cheapcar.ui

import android.util.Log
import com.auto.cheapcar.CarRepository
import com.auto.cheapcar.R
import com.auto.cheapcar.entity.bo.Brand
import com.auto.cheapcar.entity.bo.Date
import com.auto.cheapcar.entity.bo.Type
import com.auto.cheapcar.utils.manager.InternetManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class BuildDatePresenter @Inject constructor(private val carsRepository: CarRepository,
                                             private val internetManager: InternetManager) :
        BasePresenter<BuildDatePresenter.View> {

    override lateinit var view: View
    private lateinit var disposable: Disposable
    private var dates: List<Date> = emptyList()
    private lateinit var brand: Brand
    private lateinit var mainType: Type

    override fun bind(view: View) {
        this.view = view
        loadData()
    }

    override fun unbind() {
        disposable.dispose()
    }

    internal fun initializeWithData(brand: Brand, mainType: Type) {
        this.brand = brand
        this.mainType = mainType
    }

    internal fun onBindRowViewAtPosition(position: Int, rowView: BuildDateFragment.RowView) {
        rowView.setTitle(dates[position].date.toString())
        rowView.setBackground(if (position % 2 == 0) R.drawable.list_item_clicked_light
        else R.drawable.list_item_clicked_dark)
        rowView.setOnItemClickListener(android.view.View.OnClickListener {
            view.selectBuildDate(brand.title, mainType.title, dates[position].date.toString())
        })
    }

    internal fun getTypesCount(): Int {
        return dates.size
    }

    private fun loadData() {
        disposable = carsRepository.getBuildDates(brand.id, mainType)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ if (!internetManager.connectionAvailable()) view.showNoInternetMessage() })
                .doOnSubscribe({ view.showLoading(true) })
                .doOnNext({ view.showLoading(false) })
                .doOnError({ view.showLoading(false) })
                .subscribe({ dates ->
                    updateDates(dates)
                }, { error ->
                    Log.e("BuildDate", "Error", error)
                })
    }

    private fun updateDates(dates: List<Date>) {
        this.dates = dates
        view.updateDates()
    }

    interface View : PresentableView<BuildDatePresenter> {
        fun showNoInternetMessage()

        fun updateDates()

        fun selectBuildDate(brand: String, type: String, date: String)

        fun showLoading(show: Boolean)
    }
}
