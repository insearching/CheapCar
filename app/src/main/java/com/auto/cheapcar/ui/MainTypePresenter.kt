package com.auto.cheapcar.ui

import android.content.SharedPreferences
import android.util.Log
import com.auto.cheapcar.CarRepository
import com.auto.cheapcar.CheapCarApplication
import com.auto.cheapcar.R
import com.auto.cheapcar.entity.bo.Brand
import com.auto.cheapcar.entity.bo.Type
import com.auto.cheapcar.utils.manager.InternetManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainTypePresenter @Inject constructor(private val carsRepository: CarRepository,
                                            private val prefs: SharedPreferences,
                                            private val internetManager: InternetManager) :
        BasePresenter<MainTypePresenter.View> {

    override lateinit var view: View
    private lateinit var disposable: Disposable
    private var types: List<Type> = emptyList()
    private lateinit var brand: Brand

    override fun bind(view: View) {
        this.view = view
        loadData()
    }

    override fun unbind() {
        disposable.dispose()
    }

    internal fun initializeWithData(brand: Brand) {
        this.brand = brand
    }

    internal fun onBindRowViewAtPosition(position: Int, rowView: MainTypeFragment.RowView) {
        rowView.setTitle(types[position].title)
        rowView.setBackground(if (position % 2 == 0) R.drawable.list_item_clicked_light else R
                .drawable.list_item_clicked_dark)
        rowView.setOnItemClickListener(android.view.View.OnClickListener {
            carsRepository.getMainTypeIdByTitle(brand.id, types[position].title)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { type ->
                        view.selectMainType(brand, type)
                    }
        })
    }

    internal fun getTypesCount(): Int {
        return types.size
    }

    internal fun checkForMoreItems(visibleItemCount: Int, totalItemCount: Int,
                                   firstVisibleItemPosition: Int) {
        val totalPageCount = prefs.getInt(CarRepository.MAIN_TYPE_PAGE_COUNT, 0)
        if (!carsRepository.isLoading && pagesLoaded() < totalPageCount) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= CheapCarApplication.PAGE_SIZE) {
                loadData()
            }
        }
    }

    private fun loadData() {
        disposable = carsRepository.getMainTypes(brand.id, pagesLoaded(), CheapCarApplication.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ if (!internetManager.connectionAvailable()) view.showNoInternetMessage() })
                .doOnSubscribe({ view.showLoading(true) })
                .doOnNext({ view.showLoading(false) })
                .doOnError({ view.showLoading(false) })
                .subscribe({ types ->
                    updateTypes(types)
                }, { error ->
                    Log.e("MainType", "Error", error)
                })
    }

    private fun updateTypes(types: List<Type>) {
        this.types = types
        if(types.isNotEmpty()) {
            view.updateTypes()
        } else {
            view.showNoDataMessage()
        }
    }

    private fun pagesLoaded() = prefs.getInt(CarRepository.MAIN_TYPE_PAGE_LOADED, 0)

    interface View : PresentableView<MainTypePresenter> {
        fun updateTypes()

        fun showNoInternetMessage()

        fun selectMainType(brand: Brand, mainType: Type)

        fun showLoading(show: Boolean)

        fun showNoDataMessage()
    }
}
