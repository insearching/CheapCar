package com.auto.cheapcar.ui

import javax.inject.Inject

class MainPresenter @Inject constructor(): BasePresenter<MainPresenter.View>{

    override lateinit var view: MainPresenter.View

    override fun bind(view: View) {
        this.view = view
        view.switchToManufacturerScreen()
    }

    override fun unbind() {
    }

    interface View : PresentableView<MainPresenter>{
        fun switchToManufacturerScreen()
    }
}