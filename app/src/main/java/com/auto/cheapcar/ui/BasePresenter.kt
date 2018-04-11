package com.auto.cheapcar.ui

interface BasePresenter<V> {

    var view : V

    fun bind(view: V)

    fun unbind()
}